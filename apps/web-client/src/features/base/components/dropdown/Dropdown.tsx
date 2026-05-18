import { DropdownMenu, DropdownMenuContent, DropdownMenuTrigger } from '@app/components/ui/dropdown-menu'

interface BaseDropdownType {
	header: React.ReactNode
	content: React.ReactNode
}

export function BaseDropDown({ header, content }: BaseDropdownType) {
	return (
		<DropdownMenu>
			<DropdownMenuTrigger asChild>{header}</DropdownMenuTrigger>

			<DropdownMenuContent className="rounded-md">{content}</DropdownMenuContent>
		</DropdownMenu>
	)
}
