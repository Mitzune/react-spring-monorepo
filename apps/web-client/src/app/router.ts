import { AuthView } from '@features/auth/AuthView'
import { createBrowserRouter } from 'react-router'

const router = createBrowserRouter([
	{
		path: '/login',
		index: true,
		Component: AuthView,
	},
])

export { router }
