import { Avatar, AvatarImage } from '@app/components/ui/avatar'
import { Button } from '@app/components/ui/button'
import { useSidebar } from '@app/components/ui/sidebar'
import { useUserStore } from '@features/user/store/useUserStore'
import { IconMenu } from '@tabler/icons-react'

export function Header() {
	const user = useUserStore((state) => state.user)
	const { toggleSidebar, isMobile } = useSidebar()

	return (
		<header className="h-16 p-2 px-4 bg-sidebar [--radius:var(--radius-xl)] text-sidebar-foreground w-full flex align-center justify-between">
			{isMobile && (
				<Button onClick={() => toggleSidebar()} type="button" variant={'ghost'} className="my-auto">
					<IconMenu className="size-4" />
				</Button>
			)}

			<button className="flex gap-4 items-center">
				<Avatar>
					<AvatarImage src="https://github.com/shadcn.png" />
				</Avatar>

				<div className="justify-start flex-col items-start hidden md:flex">
					<p className="font-semibold text-md">{user?.nickname}</p>
					<p className="text-sm text-secondary-foreground/60">{user?.email}</p>
				</div>
			</button>
		</header>
	)
}
