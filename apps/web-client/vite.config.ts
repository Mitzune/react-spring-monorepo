import path from 'node:path'
import process from 'node:process'
import react from '@vitejs/plugin-react'
import { defineConfig, loadEnv } from 'vite'

// https://vite.dev/config/
export default defineConfig(({ mode }) => {
	const env = loadEnv(mode, process.cwd(), '')

	return {
		plugins: [react()],
		server: {
			port: Number(env.VITE_DEV_SERVER_PORT) || 3000,
		},
		resolve: {
			alias: {
				'@features': path.resolve(__dirname, 'src/features'),
				'@app': path.resolve(__dirname, 'src/app'),
			},
		},
	}
})
