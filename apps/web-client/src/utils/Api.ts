import { clearAccessToken, getAccessToken, setAccessToken } from '@features/auth/store/useAuthStore'
import type { FetchOptions } from 'ofetch'
import { $fetch, FetchError, ofetch } from 'ofetch'

const BASE_URL = import.meta.env.VITE_API_BASE_URL
let refreshPromise: Promise<string | undefined> | null = null
let isRedirecting = false

async function refreshAccessToken(): Promise<string | undefined> {
	if (refreshPromise) return refreshPromise

	refreshPromise = (async () => {
		try {
			const { accessToken } = await $fetch<{ accessToken: string }>(`${BASE_URL}/api/v1/auth/refresh`, {
				method: 'POST',
				credentials: 'include',
			})

			if (!accessToken) {
				throw new Error('Missing access token in refresh response')
			}

			setAccessToken(accessToken)
			return accessToken
		} catch (e) {
			console.error(e)
			return undefined
		} finally {
			refreshPromise = null
		}
	})()

	return refreshPromise
}

function redirectToLogin() {
	if (isRedirecting) return
	isRedirecting = true

	clearAccessToken()

	if (window.location.pathname !== '/login') {
		window.location.href = '/login'
	}

	setTimeout(() => {
		isRedirecting = false
	}, 2000)
}

const apiClient = ofetch.create({
	baseURL: BASE_URL,
	retry: false,
	onRequest({ options }) {
		const token = getAccessToken()

		const headers = new Headers(options.headers)
		if (token) {
			headers.set('Authorization', `Bearer ${token}`)
		}

		options.headers = headers
	},
})

async function apiFetch<T>(request: string, options: FetchOptions<'json'>): Promise<T> {
	try {
		return await apiClient<T>(request, options)
	} catch (err) {
		if (!(err instanceof FetchError) || err.status !== 401) throw err

		if (request.includes('/api/v1/auth/refresh')) {
			redirectToLogin()
			throw err
		}

		const newToken = await refreshAccessToken()

		if (!newToken) {
			redirectToLogin()
			throw err
		}

		return await apiClient<T>(request, {
			...options,
			headers: {
				...options.headers,
				Authorization: `Bearer ${newToken}`,
			},
		})
	}
}

const Api = {
	get: <T = unknown>(url: string, opts?: FetchOptions<'json'>) => apiFetch<T>(url, { method: 'GET', ...opts }),

	post: <T = unknown, B extends Record<string, unknown> = Record<string, unknown>>(
		url: string,
		body: B,
		opts?: FetchOptions<'json'>,
	) => apiFetch<T>(url, { method: 'POST', body, ...opts }),

	put: <T = unknown, B extends Record<string, unknown> = Record<string, unknown>>(
		url: string,
		body: B,
		opts?: FetchOptions<'json'>,
	) => apiFetch<T>(url, { method: 'PUT', body, ...opts }),

	patch: <T = unknown, B extends Record<string, unknown> = Record<string, unknown>>(
		url: string,
		body: B,
		opts?: FetchOptions<'json'>,
	) => apiFetch<T>(url, { method: 'PATCH', body, ...opts }),

	delete: <T = unknown>(url: string, opts?: FetchOptions<'json'>) => apiFetch<T>(url, { method: 'DELETE', ...opts }),
}

export { Api }
