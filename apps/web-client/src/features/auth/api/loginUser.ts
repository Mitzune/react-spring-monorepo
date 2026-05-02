import { setUser } from '@features/user/store/useUserStore'
import type { User } from '@features/user/types/User'
import { Api } from '@utils/Api'
import { useState } from 'react'
import { useNavigate } from 'react-router'
import { toast } from 'sonner'

import { setAccessToken } from '../store/useAuthStore'
import type { FirebaseAuthResponse } from '../utils/auth'

export function useLogin() {
	const navigate = useNavigate()
	const [isLoading, setIsLoading] = useState(false)

	const executeLogin = async (provider: () => Promise<FirebaseAuthResponse>, ssoType: string) => {
		try {
			setIsLoading(true)
			const { data: userCredentials, error } = await provider()

			if (error || !userCredentials) {
				toast.warning(error ?? 'Something went wrong!')
				return
			}

			try {
				const { user, token } = await Api.post<{ user: User; token: string }>(
					`/api/auth/${ssoType}`,
					{ ...userCredentials },
					{ credentials: 'include' },
				)
				setAccessToken(token)
				setUser(user)
				navigate('/')
			} catch {
				setIsLoading(false)
				toast.warning('Something went wrong!')
			}

			setIsLoading(false)
		} catch {
			setIsLoading(false)
			toast.warning('Something went wrong!')
		}
	}

	return { executeLogin, isLoading }
}
