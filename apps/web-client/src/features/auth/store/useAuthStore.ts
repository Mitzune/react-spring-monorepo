import { create } from 'zustand'

interface AuthStoreState {
	accessToken: string
	isAuthorized: boolean
}

interface AuthStoreAction {
	setAccessToken: (token: string) => void
	clearAccessToken: () => void
}

const useAuthStore = create<AuthStoreState & AuthStoreAction>((set) => ({
	accessToken: '',
	isAuthorized: false,

	setAccessToken: (token: string) => set({ accessToken: token, isAuthorized: true }),
	clearAccessToken: () => set({ accessToken: '', isAuthorized: false }),
}))

export const getIsAuthorized = () => useAuthStore.getState().isAuthorized
export const getAccessToken = () => useAuthStore.getState().accessToken
export const setAccessToken = (token: string) => useAuthStore.getState().setAccessToken(token)

export const clearAccessToken = () => useAuthStore.getState().clearAccessToken()

export { useAuthStore }
