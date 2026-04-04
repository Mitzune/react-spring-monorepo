function get(url: string, options: AnyObject) {
	options = {
		...options,
		'Content-Type': 'application/json',
		Accept: 'application/json',
	}

	return fetch(url, options).then((res) => res.json())
}

function post(url: string, { arg }: { arg: AnyObject }) {
	const headerValue = {
		'Content-Type': 'application/json',
		Accept: 'application/json',
	}

	const baseUrl = 'http://localhost:8080'

	return fetch(`${baseUrl}${url}`, {
		method: 'POST',
		headers: {
			...headerValue,
		},
		body: JSON.stringify(arg),
	}).then((res) => res.json())
}

const Api = {
	get,
	post,
}

export { Api }
