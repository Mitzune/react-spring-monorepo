import { create } from 'zustand'

interface AuthStoreState {
	accessToken: string
}

interface AuthStoreAction {
	setAccessToken: (token: string) => void
}

const useAuthStore = create<AuthStoreState & AuthStoreAction>((set) => ({
	accessToken: '',

	setAccessToken: (token: string) => set({ accessToken: token }),
}))

export const getAccessToken = () => useAuthStore.getState().accessToken
export const setAccessToken = (token: string) => useAuthStore.getState().setAccessToken(token)

export { useAuthStore }
