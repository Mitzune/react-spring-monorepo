import { Avatar, AvatarImage } from '@app/components/ui/avatar'
import { Button } from '@app/components/ui/button'
import { DropdownMenuItem } from '@app/components/ui/dropdown-menu'
import { InputGroup, InputGroupAddon, InputGroupInput } from '@app/components/ui/input-group'
import { Kbd } from '@app/components/ui/kbd'
import { BaseDropDown } from '@features/base/components/dropdown/Dropdown'
import { useUserStore } from '@features/user/store/useUserStore'
import { IconBell, IconSettings, IconUser } from '@tabler/icons-react'

export function Header() {
	const user = useUserStore((state) => state.user)

	return (
		<header
			className="bg-sidebar text-sidebar-foreground align-center flex h-16 w-full items-center justify-end gap-4
				rounded-b-lg p-2 px-4 [--radius:var(--radius-xl)] md:rounded-none"
		>
			<InputGroup className="w-52 rounded-md">
				<InputGroupInput className="text-xs md:text-base" placeholder="Search..."></InputGroupInput>

				<InputGroupAddon className="hidden md:block" align={'inline-end'}>
					<Kbd>⌘</Kbd>
					<Kbd>K</Kbd>
				</InputGroupAddon>
			</InputGroup>

			<div className="flex items-center gap-2">
				<Button type="button" className="bg-yellow-400/20 text-yellow-400">
					<IconBell />
				</Button>

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
			</div>
		</header>
	)
}
