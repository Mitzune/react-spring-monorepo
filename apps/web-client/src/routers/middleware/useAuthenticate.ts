import { fetchUser } from '@features/user/api/fetchUser'
import { useUserStore } from '@features/user/store/useUserStore'
import { redirect } from 'react-router'

export async function userAuthenticate({ request }: { request: AnyObject }) {
	// if user or auth is logged skip

	const url = new URL(request.url)
	const pathname = url.pathname
	try {
		const user = await fetchUser()

		if (!user) {
			if (pathname !== '/login') {
				throw redirect('/login')
			}
			return
		}

		if (pathname === '/login') {
			throw redirect('/')
		}

		useUserStore.getState().setUser(user)
	} catch (error) {
		if (error instanceof Response) {
			throw error
		}

		if (pathname !== '/login') {
			throw redirect('/login')
		}
	}
}
