import { AuthView } from '@features/auth/pages/AuthView'
import { NotFound } from '@features/base/components/not-found/NotFound'
import { DashboardView } from '@features/dashboard/pages/DashboardView'
import { fetchByUsername } from '@features/user/api/fetchByUsername'
import { UserView } from '@features/user/pages/UserView'
import { DashboardLayout } from '@layouts/dashboard/DashboardLayout'
import { createBrowserRouter } from 'react-router'

import { userAuthenticate } from './middleware/useAuthenticate'

const router = createBrowserRouter([
	{
		path: '/',
		errorElement: <NotFound />,
	},
	{
		path: '/login',
		index: true,
		loader: userAuthenticate,
		element: <AuthView />,
	},
	{
		path: '/',
		loader: userAuthenticate,
		element: <DashboardLayout />,
		children: [
			{
				id: 'index',
				path: '/',
				index: true,
				element: <DashboardView />,
			},
			{
				id: 'user-view',
				loader: fetchByUsername,
				path: '/user/:username',
				element: <UserView />,
			},
		],
	},
])

export { router }
