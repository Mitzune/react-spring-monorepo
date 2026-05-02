import { create } from 'zustand'

import type { User } from '../types/User'

interface UseUserStoreState {
	user: User | null
}

interface UseUserStoreAction {
	setUser: (user: User) => void
}

const useUserStore = create<UseUserStoreState & UseUserStoreAction>((set) => ({
	user: null,

	setUser: (user: User) => set({ user }),
}))

export const setUser = (user: UseUserStoreState['user']) => useUserStore.setState({ user })

export { useUserStore }
