import { Avatar, AvatarImage } from '@app/components/ui/avatar'
import { Button } from '@app/components/ui/button'
import { DropdownMenuItem } from '@app/components/ui/dropdown-menu'
import { useSidebar } from '@app/components/ui/sidebar'
import { BaseDropDown } from '@features/base/components/dropdown/Dropdown'
import { useUserStore } from '@features/user/store/useUserStore'
import { IconMenu, IconSettings, IconUser } from '@tabler/icons-react'

export function Header() {
	const user = useUserStore((state) => state.user)
	const { toggleSidebar, isMobile } = useSidebar()

	return (
		<header
			className="text-sidebar-foreground align-center flex h-16 w-full justify-between p-2 px-4
				[--radius:var(--radius-xl)]"
		>
			<div>
				{isMobile && (
					<Button onClick={() => toggleSidebar()} type="button" variant={'ghost'} className="my-auto">
						<IconMenu className="size-4" />
					</Button>
				)}
			</div>

			<BaseDropDown
				header={
					<button className="flex items-center gap-4">
						<Avatar>
							<AvatarImage src="https://github.com/shadcn.png" />
						</Avatar>

						<div className="hidden flex-col items-start justify-start md:flex">
							<p className="text-md font-semibold">{user?.nickname}</p>
							<p className="text-secondary-foreground/60 text-sm">{user?.email}</p>
						</div>
					</button>
				}
				content={
					<>
						<DropdownMenuItem>
							<IconUser />
							Profile
						</DropdownMenuItem>

						<DropdownMenuItem>
							<IconSettings />
							Settings
						</DropdownMenuItem>
					</>
				}
			/>
		</header>
	)
}
