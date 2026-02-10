import { Button, Flex, Grid, TextInput, Title } from '@mantine/core'
import { useForm } from '@mantine/form'

export function PostCreateView() {
	const form = useForm({
		mode: 'uncontrolled',

		initialValues: {
			message: '',
			author: '',
		},

		validate: {
			message: (value) =>
				value
					? value.length > 4
						? null
						: 'Message cannot be less than 4 characters.'
					: 'Message cannot be empty!',
			author: (value) =>
				value
					? value.length > 4
						? null
						: 'Author name cannot be less than 4 characters.'
					: 'Author cannot be empty!',
		},
	})

	const onSubmit = () => {}

	return (
		<form onSubmit={form.onSubmit(() => onSubmit())}>
			<Flex align={'center'} justify={'center'}>
				<Grid w={600} gutter={'lg'}>
					<Grid.Col span={12}>
						<Title c={'orange'}>Create post</Title>
					</Grid.Col>

					<Grid.Col span={12}>
						<TextInput
							label="Author"
							placeholder="Enter author name here."
							key={form.key('author')}
							{...form.getInputProps('author')}
							withAsterisk
						/>
					</Grid.Col>

					<Grid.Col span={12}>
						<TextInput
							label="Message"
							placeholder="Enter message here."
							key={form.key('message')}
							{...form.getInputProps('message')}
							withAsterisk
						/>
					</Grid.Col>

					<Flex w={'100%'} justify={'center'} align={'center'}>
						<Button type="submit">Submit</Button>
					</Flex>
				</Grid>
			</Flex>
		</form>
	)
}
