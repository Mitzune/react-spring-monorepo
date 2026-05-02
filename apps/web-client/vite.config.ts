import process from 'node:process'

import tailwindcss from '@tailwindcss/vite'
import react from '@vitejs/plugin-react'
import { defineConfig, loadEnv } from 'vite'
import tsconfigPaths from 'vite-tsconfig-paths'

// https://vite.dev/config/
export default defineConfig(({ mode }) => {
	const env = loadEnv(mode, process.cwd(), '')

	return {
		plugins: [react(), tailwindcss(), tsconfigPaths({ projects: ['./tsconfig.json'] })],
		server: {
			port: Number(env.VITE_DEV_SERVER_PORT) || 3000,
		},
		build: {
			outDir: '../../build/web-client',
		},
	}
})
