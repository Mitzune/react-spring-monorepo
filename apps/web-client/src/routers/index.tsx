import { AuthView } from '@features/auth/AuthView'
import { NotFound } from '@features/base/components/not-found/NotFound'
import { DashboardView } from '@features/dashboard/DashboardView'
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
		],
	},
])

export { router }
