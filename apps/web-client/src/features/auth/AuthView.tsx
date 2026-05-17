import { Button } from '@app/components/ui/button'
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@app/components/ui/card'
import { Separator } from '@app/components/ui/separator'
import { IconBusinessplan } from '@tabler/icons-react'

import { useLogin } from './api/loginUser'
import { ssoDetails } from './constant/SsoDetails'
import { loginWithGoogle } from './utils/auth'

export function AuthView() {
	const { executeLogin, isLoading } = useLogin()

	return (
		<main className="bg-muted/40 flex min-h-svh w-full items-center justify-center px-4 py-8">
			<Card className="w-full max-w-sm shadow-lg">
				<CardHeader className="space-y-4 pb-2">
					<div className="flex flex-col items-center gap-2">
						<div className="bg-primary/10 flex size-14 items-center justify-center rounded-xl">
							<IconBusinessplan className="text-primary size-8" />
						</div>
						<h1 className="text-lg font-semibold tracking-tight">Business Inc.</h1>
					</div>

					<div className="space-y-1">
						<CardTitle className="text-center text-xl sm:text-2xl">Welcome back</CardTitle>
						<CardDescription className="text-center text-sm">
							Sign in to continue to your account
						</CardDescription>
					</div>
				</CardHeader>

				<CardContent className="space-y-4">
					<form
						onSubmit={(e) => {
							e.preventDefault()
							executeLogin(loginWithGoogle, 'google')
						}}
					>
						<Button
							variant="outline"
							className="hover:bg-accent w-full gap-2 rounded-lg py-5 text-sm font-medium
								transition-colors"
							isLoading={isLoading}
							type="submit"
						>
							{!isLoading && ssoDetails.Google.icon}
							{ssoDetails.Google.placeholder}
						</Button>
					</form>

					<Separator />

					<p className="text-muted-foreground text-center text-xs">
						By continuing, you agree to our Terms of Service and Privacy Policy.
					</p>
				</CardContent>
			</Card>
		</main>
	)
}
