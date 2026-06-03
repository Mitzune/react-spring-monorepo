import { clearAccessToken, getAccessToken, setAccessToken } from '@features/auth/store/useAuthStore'
import type { FetchOptions } from 'ofetch'
import { $fetch, ofetch } from 'ofetch'

function setToken(token: string) {
	setAccessToken(token)
}

function getToken() {
	return getAccessToken()
}

let refreshPromise: Promise<string | undefined> | null = null

async function refetchAccessToken(options: AnyObject): Promise<string | undefined> {
	if (refreshPromise) return refreshPromise

	refreshPromise = (async () => {
		try {
			const { accessToken } = await $fetch<{ accessToken: string }>(`${options.baseURL}/api/v1/auth/refresh`, {
				method: 'POST',
				credentials: 'include',
			})

			if (!accessToken) {
				throw new Error('Missing access token in refresh response')
			}

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
	clearAccessToken()
	const path = window.location.pathname

	if (path === '/login') return

	window.location.href = '/login'
}

const apiClient = ofetch.create({
	baseURL: import.meta.env.VITE_API_BASE_URL,
	onRequest({ options }) {
		const token = getToken()
		const headers = new Headers(options.headers)
		if (!headers.has('Content-Type')) {
			headers.set('Content-Type', 'application/json')
		}
		if (token) {
			headers.set('Authorization', `Bearer ${token}`)
		}

		options.headers = headers
	},
	async onResponse(context) {
		const { response, options, request } = context

		if (response?.status === 401) {
			const accessToken = await refetchAccessToken(options)

			if (!accessToken) {
				redirectToLogin()
				return
			}

			setToken(accessToken)

			try {
				await $fetch(request, {
					...options,
					headers: {
						...options.headers,
						Authorization: `Bearer ${accessToken}`,
					},
					retry: false,
					onResponse(ctx: AnyObject) {
						Object.assign(context, ctx)
					},
				})
			} catch {
				redirectToLogin()
			}
		}
	},
})

const Api = {
	get: <T = unknown>(url: string, opts?: FetchOptions<'json'>) => apiClient<T>(url, { method: 'GET', ...opts }),

	post: <T = unknown, B extends Record<string, unknown> = Record<string, unknown>>(
		url: string,
		body: B,
		opts?: FetchOptions<'json'>,
	) => apiClient<T>(url, { method: 'POST', body, ...opts }),

	put: <T = unknown, B extends Record<string, unknown> = Record<string, unknown>>(
		url: string,
		body: B,
		opts?: FetchOptions<'json'>,
	) => apiClient<T>(url, { method: 'PUT', body, ...opts }),

	patch: <T = unknown, B extends Record<string, unknown> = Record<string, unknown>>(
		url: string,
		body: B,
		opts?: FetchOptions<'json'>,
	) => apiClient<T>(url, { method: 'PATCH', body, ...opts }),

	delete: <T = unknown>(url: string, opts?: FetchOptions<'json'>) => apiClient<T>(url, { method: 'DELETE', ...opts }),
}

export { Api }
