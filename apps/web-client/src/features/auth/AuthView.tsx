import { Button } from '@app/components/ui/button'
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@app/components/ui/card'

import { useLogin } from './api/loginUser'
import { ssoDetails } from './constant/SsoDetails'
import { loginWithGoogle } from './utils/auth'

export function AuthView() {
	const { executeLogin, isLoading } = useLogin()

	return (
		<main className="flex w-full items-center justify-center">
			<Card className="w-100">
				<CardHeader>
					<CardTitle>Login to your account</CardTitle>
					<CardDescription>Enter your email below to login to your account</CardDescription>
				</CardHeader>

				<CardContent>
					<form
						onSubmit={(e) => {
							e.preventDefault()
							executeLogin(loginWithGoogle, 'Google')
						}}
					>
						<Button variant={'outline'} className="w-full" isLoading={isLoading} type="submit">
							{!isLoading && ssoDetails.Google.icon}
							{ssoDetails.Google.placeholder}
						</Button>
					</form>
				</CardContent>
			</Card>
		</main>
	)
}
