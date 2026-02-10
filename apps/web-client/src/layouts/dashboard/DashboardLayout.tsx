import { Header } from '@layouts/dashboard/components/index'
import { AppShell, Container } from '@mantine/core'
import { Outlet } from 'react-router'

export function DashboardLayout() {
	return (
		<AppShell>
			<AppShell.Header>
				<Header />
			</AppShell.Header>

			<AppShell.Main mt={'128'}>
				<Container size={1400}>
					<Outlet />
				</Container>
			</AppShell.Main>
		</AppShell>
	)
}
