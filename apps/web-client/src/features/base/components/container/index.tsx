import { cn } from '@app/lib/utils'
import type { ComponentProps, PropsWithChildren } from 'react'

export function Container({ children, className }: PropsWithChildren<ComponentProps<'div'>>) {
	return <div className={cn('m-2 p-2', className)}>{children}</div>
}
