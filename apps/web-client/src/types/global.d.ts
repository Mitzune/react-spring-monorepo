export {}

declare global {
	// eslint-disable-next-line @typescript-eslint/no-explicit-any
	type AnyObject = Record<string, any>
	// eslint-disable-next-line @typescript-eslint/no-explicit-any
	type AnyFunction = (...args: any[]) => any
}
