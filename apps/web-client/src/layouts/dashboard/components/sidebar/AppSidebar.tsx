import { Button } from '@app/components/ui/button'
import { Collapsible, CollapsibleContent, CollapsibleTrigger } from '@app/components/ui/collapsible'
import {
	Sidebar,
	SidebarContent,
	SidebarFooter,
	SidebarGroup,
	SidebarGroupContent,
	SidebarGroupLabel,
	SidebarHeader,
	SidebarMenu,
	SidebarMenuItem,
} from '@app/components/ui/sidebar'
import { IconBusinessplan, IconChevronDown } from '@tabler/icons-react'
import { sidebarLinks } from './sidebar.config'

export function AppSidebar() {
	return (
		<Sidebar>
			<SidebarHeader className="h-16 flex flex-row items-center justify-center">
				<IconBusinessplan />
				<p className="text-3xl">Business</p>
			</SidebarHeader>
			<SidebarContent>
				{sidebarLinks.map((sidebarConfig) => (
					<Collapsible defaultOpen={sidebarConfig.isDefaultOpen} className="group/collapsible">
						<SidebarGroup>
							<SidebarGroupLabel asChild>
								<CollapsibleTrigger>
									{sidebarConfig.label}
									<IconChevronDown className="ml-auto transition-transform group-data-[state=open]/collapsible:rotate-180" />
								</CollapsibleTrigger>
							</SidebarGroupLabel>
							<CollapsibleContent>
								<SidebarGroupContent>
									<SidebarMenu>
										{sidebarConfig.items.map((link) => (
											<SidebarMenuItem>
												<Button
													type="button"
													className="w-full flex align-start justify-start"
													variant={'ghost'}
												>
													{<link.icon />}

													<span>{link.label}</span>
												</Button>
											</SidebarMenuItem>
										))}
									</SidebarMenu>
								</SidebarGroupContent>
							</CollapsibleContent>
						</SidebarGroup>
					</Collapsible>
				))}
			</SidebarContent>

			<SidebarFooter />
		</Sidebar>
	)
}
