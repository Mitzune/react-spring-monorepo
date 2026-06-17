import { Api } from '@utils/Api'

import type { User } from '../types/User'

export async function fetchUser(): Promise<User | undefined> {
	try {
		const user = await Api.get<User>('/api/v1/users')
		return user
	} catch (e) {
		console.error(e)
	}
}
