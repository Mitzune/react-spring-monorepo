export interface User {
	id: string
	username: string
	displayName: string
	userRole: (typeof UserRole)[keyof typeof UserRole]
	email: string
	createdAt: Date
	updatedAt: Date
}

export const UserRole = {
	EMPLOYEE: 'EMPLOYEE',
	ADMIN: 'ADMIN',
} as const
