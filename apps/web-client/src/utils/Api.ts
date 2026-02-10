interface TypeQueryOptions {
	path: string
	baseUrl?: string
}

async function get(queryParams: AnyObject, options: TypeQueryOptions) {
	const baseUrl = options?.baseUrl ?? import.meta.env.VITE_API_BASE_URL

	const query = new URLSearchParams(queryParams).toString()

	const response = await fetch(`${baseUrl}${options.path}?${query}`)

	return response.json()
}

async function post(body: AnyObject, options: TypeQueryOptions) {
	const baseUrl = options?.baseUrl ?? import.meta.env.VITE_API_BASE_URL

	const response = await fetch(`${baseUrl}${options.path}`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
		},
		body: JSON.stringify(body),
	})

	return response.json()
}

export default {
	get: async (queryParams: AnyObject, options: TypeQueryOptions) => await get(queryParams, options),
	post: async (body: AnyObject, options: TypeQueryOptions) => await post(body, options),
}
