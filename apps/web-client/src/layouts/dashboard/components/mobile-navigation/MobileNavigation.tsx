import { Button } from '@app/components/ui/button'
import { cn } from '@app/lib/utils'
import { Container } from '@features/base/components/container'
import { useLocation } from 'react-router'

import { links } from './navigation'

export function MobileNavigation() {
	const { pathname } = useLocation()

	return (
		<Container
			className="bg-sidebar ring-sidebar-border fixed right-0 bottom-0 left-0 mx-8 flex h-16 justify-between gap-4
				rounded-lg ring-1"
		>
			{links.map((link, key) => (
				<Button
					type="button"
					variant={pathname === link.path ? 'default' : 'ghost'}
					className={cn('h-12 flex-1', pathname !== link.path && 'text-primary')}
					key={`mobile-navigation-${link.label}-${key}`}
					aria-label={link.label}
				>
					{<link.icon className="size-6" />}
				</Button>
			))}
		</Container>
	)
}
