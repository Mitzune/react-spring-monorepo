import { useLoaderData } from 'react-router'

export function UserView() {
	const user = useLoaderData()

	return <p>user details {JSON.stringify(user)}</p>
}
