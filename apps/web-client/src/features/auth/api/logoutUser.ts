import { setUser } from '@features/user/store/useUserStore'
import { Api } from '@utils/Api'

import { setAccessToken } from '../store/useAuthStore'

export async function logoutUser() {
	// clear zustand stale data

	try {
		await Api.post('/api/v1/auth/logout', {}, { credentials: 'include' })
	} catch (e) {
		console.error(e)
	} finally {
		setUser(null)
		setAccessToken('')
		window.location.href = '/'
	}
}
