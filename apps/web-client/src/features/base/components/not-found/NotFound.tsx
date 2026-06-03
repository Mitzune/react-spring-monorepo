import { Button } from '@app/components/ui/button'
import { IconMoodPuzzled } from '@tabler/icons-react'
import { useNavigate } from 'react-router'

export function NotFound() {
	const navigate = useNavigate()

	return (
		<div className="flex h-dvh flex-col items-center justify-center gap-4">
			<IconMoodPuzzled className="text-primary size-32" />

			<h1 className="text-2xl font-medium">404 Page not found</h1>

			<p className="w-[400px] text-center">
				Oops! We can't seem to find that page. Let's get you back home and back on track
			</p>

			<Button size={'lg'} variant={'secondary'} onClick={() => navigate('/login')}>
				Go back
			</Button>
		</div>
	)
}
