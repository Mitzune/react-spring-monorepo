import { getAccessToken, setAccessToken } from '@features/auth/store/useAuthStore'
import type { FetchOptions } from 'ofetch'
import { $fetch, ofetch } from 'ofetch'

function setToken(token: string) {
	setAccessToken(token)
}

function getToken() {
	return getAccessToken()
}

const apiClient = ofetch.create({
	baseURL: import.meta.env.VITE_API_BASE_URL,
	onRequest({ options }) {
		const token = getToken()
		const headers = new Headers(options.headers)
		headers.set('Content-Type', 'application/json')
		if (token) {
			headers.set('Authorization', `Bearer ${token}`)
		}

		options.headers = headers
	},
	async onResponse(context) {
		const { response, options, request } = context

		if (response?.status === 401) {
			let accessToken: string | undefined

			try {
				const { accessToken: token } = await $fetch<{ accessToken: string }>(
					`${options.baseURL}/api/v1/auth/refresh`,
					{
						method: 'POST',
						credentials: 'include',
					},
				)

				accessToken = token

				if (!accessToken) {
					throw new Error('Missing access token in refresh response')
				}
			} catch (e) {
				console.error(e)
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
				console.error('Authentication error')
			}
		}
	},
})

const Api = {
	get: <T = unknown>(url: string, opts?: FetchOptions<'json'>) => apiClient<T>(url, { method: 'GET', ...opts }),

	post: <T = unknown>(url: string, body: AnyObject, opts?: FetchOptions<'json'>) =>
		apiClient<T>(url, { method: 'POST', body, ...opts }),
}

export { Api }
