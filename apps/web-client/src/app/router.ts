import { createBrowserRouter } from 'react-router'
import { DashboardView } from '../features/dashboard/DashboardView'

const router = createBrowserRouter([{ path: '/', Component: DashboardView }])

export { router }
