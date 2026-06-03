import { create } from 'zustand'

interface AuthStoreState {
	accessToken: string
}

interface AuthStoreAction {
	setAccessToken: (token: string) => void
	clearAccessToken: () => void
}

const useAuthStore = create<AuthStoreState & AuthStoreAction>((set) => ({
	accessToken: '',

	setAccessToken: (token: string) => set({ accessToken: token }),
	clearAccessToken: () => set({ accessToken: '' }),
}))

export const getAccessToken = () => useAuthStore.getState().accessToken
export const setAccessToken = (token: string) => useAuthStore.getState().setAccessToken(token)
export const clearAccessToken = () => useAuthStore.getState().clearAccessToken()

export { useAuthStore }
