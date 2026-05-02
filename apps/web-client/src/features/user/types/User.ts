export interface User {
	id: string
	nickname: string
	userRole: (typeof UserRole)[keyof typeof UserRole]
	email: string
	createdAt: Date
	updatedAt: Date
}

export const UserRole = {
	EMPLOYEE: 'EMPLOYEE',
	ADMIN: 'ADMIN',
} as const
