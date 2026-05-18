import {
	Sidebar,
	SidebarContent,
	SidebarFooter,
	SidebarHeader,
	SidebarMenu,
	SidebarMenuButton,
	SidebarMenuItem,
} from '@app/components/ui/sidebar'
import { IconBusinessplan } from '@tabler/icons-react'
import { useLocation } from 'react-router'

import { sidebarLinks } from './sidebar.config'

export function AppSidebar() {
	const { pathname } = useLocation()

	return (
		<Sidebar>
			<SidebarHeader className="flex h-16 flex-row items-center justify-center">
				<IconBusinessplan className="text-primary" />
				<p className="text-xl">Business</p>
			</SidebarHeader>
			<SidebarContent>
				{sidebarLinks.map((sidebarConfig, key) => (
					<SidebarMenu key={`sidebarConfig-${sidebarConfig.label}-${key}`} className="space-x-1">
						{sidebarConfig.items.map((link, key) => (
							<SidebarMenuItem key={`${link.label}-${key}`}>
								<SidebarMenuButton asChild>
									<a
										href={link.path}
										className={`flex h-12 w-full items-center gap-3 px-3
										${pathname === link.path ? 'bg-primary text-primary-foreground' : ''} `}
									>
										<link.icon className="size-5" />
										<span>{link.label}</span>
									</a>
								</SidebarMenuButton>
							</SidebarMenuItem>
						))}
					</SidebarMenu>
				))}
			</SidebarContent>

			<SidebarFooter />
		</Sidebar>
	)
}
