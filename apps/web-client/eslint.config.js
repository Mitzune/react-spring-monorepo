import js from '@eslint/js'
import eslintConfigPrettier from 'eslint-config-prettier'
import importPlugin from 'eslint-plugin-import'
import react from 'eslint-plugin-react'
import reactHooks from 'eslint-plugin-react-hooks'
import unusedImports from 'eslint-plugin-unused-imports'
import tseslint from 'typescript-eslint'

export default [
	// Base JS rules
	js.configs.recommended,

	// TypeScript (production baseline)
	...tseslint.configs.recommended,

	{
		plugins: {
			react,
			'react-hooks': reactHooks,
			import: importPlugin,
			'unused-imports': unusedImports,
		},

		settings: {
			react: { version: 'detect' },
		},

		rules: {
			/* ----------------------------------
			 * React correctness (core production)
			 * ---------------------------------- */
			'react/react-in-jsx-scope': 'off',
			'react/jsx-key': 'error',
			'react/jsx-no-duplicate-props': 'error',
			'react/jsx-no-undef': 'error',

			/* ----------------------------------
			 * Hooks correctness (mandatory)
			 * ---------------------------------- */
			'react-hooks/rules-of-hooks': 'error',
			'react-hooks/exhaustive-deps': 'warn',

			/* ----------------------------------
			 * Import hygiene (monorepo standard)
			 * ---------------------------------- */
			'import/order': [
				'error',
				{
					'newlines-between': 'always',
					alphabetize: { order: 'asc', caseInsensitive: true },
				},
			],

			/* ----------------------------------
			 * Unused code cleanup (Cleaning)
			 * ---------------------------------- */
			'no-unused-vars': 'off',
			'unused-imports/no-unused-imports': 'error',
			'unused-imports/no-unused-vars': [
				'warn',
				{
					vars: 'all',
					varsIgnorePattern: '^_',
					args: 'after-used',
					argsIgnorePattern: '^_',
				},
			],
		},
	},

	eslintConfigPrettier,
]
