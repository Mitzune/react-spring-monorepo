import { Api } from '@utils/Api'
import { redirect } from 'react-router'

import type { User } from '../types/User'

export async function fetchByUsername({ params }: AnyObject): Promise<User | undefined> {
	const { username } = params

	if (!username) {
		throw redirect('/')
	}

	try {
		const user = await Api.get<User>(`/api/v1/users/${username}`)
		return user
	} catch {
		throw redirect('/')
	}
}
