import type { FirebaseAuthResponse } from '../utils/auth'
import { notifications } from '@mantine/notifications'
import { useMutation } from '@tanstack/react-query'
import { Api } from '@utils/Api'

export function login() {
	const { mutate, data, isPending } = useMutation({
		mutationFn: (token: { token: string }) => {
			return Api.post('/api/auth/Microsoft', token)
		},
		onError: () => {
			notifications.show({ title: 'Warning', message: 'Something went wrong!', color: 'red' })
		},
	})

	const executeLogin = async (provider: () => Promise<FirebaseAuthResponse>) => {
		const { data: userCredentials, error } = await provider()

		if (error || !userCredentials) {
			notifications.show({ title: 'Warning', message: error ?? 'Something went wrong!', color: 'red' })
			return
		}

		mutate(userCredentials)
	}

	return { executeLogin, data, isPending }
}
