import type { User } from '../types/User'
import { Api } from '@utils/Api'

export async function fetchUser() {
	const user = await Api.get<User>('/api/users')

	return user
}
