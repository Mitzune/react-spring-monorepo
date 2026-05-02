import { Api } from '@utils/Api'

import type { User } from '../types/User'

export async function fetchUser() {
	const user = await Api.get<User>('/api/users')

	return user
}
