import type { FetchOptions } from 'ofetch'
import { ofetch } from 'ofetch'

const headerOptions = {
	'Content-Type': 'application/json',
	Accept: 'application/json',
}

const apiFetch = ofetch.create({
	baseURL: 'http://localhost:8080',
	onRequest({ options }) {
		options.headers = { ...options.headers, ...headerOptions }
	},
})

const Api = {
	get: (url: string, opts?: FetchOptions) => apiFetch(url, { method: 'GET', ...opts }),

	post: (url: string, body: AnyObject, opts?: FetchOptions) => apiFetch(url, { method: 'POST', body, ...opts }),
}

export { Api }
