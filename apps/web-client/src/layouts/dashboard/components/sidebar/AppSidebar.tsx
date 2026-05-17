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
			<SidebarHeader className="flex h-16 flex-row items-center justify-center">
				<IconBusinessplan className="text-primary" />
				<p className="text-xl">Business</p>
			</SidebarHeader>
			<SidebarContent>
				{sidebarLinks.map((sidebarConfig, key) => (
					<Collapsible
						key={`sidebarConfig.label-${key}`}
						defaultOpen={sidebarConfig.isDefaultOpen}
						className="group/collapsible"
					>
						<SidebarGroup>
							<SidebarGroupLabel asChild>
								<CollapsibleTrigger>
									{sidebarConfig.label}
									<IconChevronDown
										className="ml-auto transition-transform
											group-data-[state=open]/collapsible:rotate-180"
									/>
								</CollapsibleTrigger>
							</SidebarGroupLabel>
							<CollapsibleContent>
								<SidebarGroupContent>
									<SidebarMenu>
										{sidebarConfig.items.map((link, key) => (
											<SidebarMenuItem key={`${link.label}-${key}`}>
												<Button
													type="button"
													className="align-start flex w-full justify-start"
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
