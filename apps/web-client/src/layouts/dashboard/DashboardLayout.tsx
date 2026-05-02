import { SidebarProvider } from '@app/components/ui/sidebar'
import { Header } from '@layouts/dashboard/components/index'
import { Outlet } from 'react-router'
import { AppSidebar } from './components/sidebar/AppSidebar'

export function DashboardLayout() {
	return (
		<SidebarProvider>
			<AppSidebar />

			<main className="flex flex-1 flex-col gap-2">
				<Header />

				<Outlet />
			</main>
		</SidebarProvider>
	)
}
