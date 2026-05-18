import { SidebarProvider } from '@app/components/ui/sidebar'
import { useIsMobile } from '@app/hooks/use-mobile'
import { Header, MobileNavigation } from '@layouts/dashboard/components/index'
import { Outlet } from 'react-router'

import { AppSidebar } from './components/sidebar/AppSidebar'

export function DashboardLayout() {
	const isMobile = useIsMobile()
	return (
		<SidebarProvider>
			<AppSidebar />

			<main className="bg-muted flex flex-1 flex-col gap-2 pb-16 md:pb-4">
				<Header />

				<Outlet />

				{isMobile && <MobileNavigation />}
			</main>
		</SidebarProvider>
	)
}
