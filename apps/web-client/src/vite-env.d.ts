type ViteTypeOptions = object

interface ImportMetaEnv {
	readonly VITE_APP_PORT: string
	// more env variables...
}

interface ImportMeta {
	readonly env: ImportMetaEnv
}

declare module '@fontsource-variable/inter'
