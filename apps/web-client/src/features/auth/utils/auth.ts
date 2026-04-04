import { app } from '@utils/firebase'
import { getAuth, OAuthProvider, signInWithPopup } from 'firebase/auth'

export async function loginWithMicrosoft() {
	const auth = getAuth(app)
	const provider = new OAuthProvider('microsoft.com')
	provider.setCustomParameters({
		tenant: import.meta.env.VITE_AZURE_TENANT_ID,
	})

	const result = await signInWithPopup(auth, provider)

	if (!result.user) {
		return { data: null, error: 'Error on SSO sign in' }
	}

	const { user } = result

	return {
		data: { token: await user.getIdToken() },
		error: null,
	}
}
