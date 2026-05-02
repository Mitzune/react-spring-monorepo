import { router } from '@routers/index'
import { QueryClient, QueryClientProvider } from '@tanstack/react-query'

import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { RouterProvider } from 'react-router'
import { TooltipProvider } from './components/ui/tooltip'
import '@fontsource-variable/inter'
import '@app/index.css'

const queryClient = new QueryClient()

createRoot(document.getElementById('root')!).render(
	<StrictMode>
		<QueryClientProvider client={queryClient}>
			<TooltipProvider>
				<RouterProvider router={router} />
			</TooltipProvider>
		</QueryClientProvider>
	</StrictMode>,
)
