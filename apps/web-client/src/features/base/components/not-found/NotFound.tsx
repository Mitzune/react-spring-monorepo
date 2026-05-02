import { Button } from '@app/components/ui/button'
import { IconMoodPuzzled } from '@tabler/icons-react'
import { useNavigate } from 'react-router'

export function NotFound() {
	const navigate = useNavigate()

	return (
		<div className="w-full flex flex-col">
			<IconMoodPuzzled />

			<h1>404 Page not found</h1>

			<p>Oops! We can't seem to find that page. Let's get you back home and back on track</p>

			<Button onClick={() => navigate('/login')}>Go back</Button>
		</div>
	)
}
