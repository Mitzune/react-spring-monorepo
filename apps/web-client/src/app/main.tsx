import { router } from '@app/router'
import { MantineProvider } from '@mantine/core'
import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { RouterProvider } from 'react-router'
import '@app/index.css'
import '@mantine/core/styles.css'
import '@mantine/charts/styles.css'

createRoot(document.getElementById('root')!).render(
	<StrictMode>
		<MantineProvider>
			<RouterProvider router={router} />
		</MantineProvider>
	</StrictMode>,
)
