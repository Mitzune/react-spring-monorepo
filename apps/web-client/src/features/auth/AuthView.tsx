import { Button, Card, Flex } from '@mantine/core'
import { IconBrandWindows } from '@tabler/icons-react'
import { Api } from '@utils/Api'
import useSWRMutation from 'swr/mutation'
import { FormHeader } from './components/FormHeader'
import { loginWithMicrosoft } from './utils/auth'

export function AuthView() {
	const { trigger, data } = useSWRMutation('/api/auth/Microsoft', Api.post)

	const login = async (provider: () => Promise<{ data: AnyObject | null; error: string | null }>) => {
		const { data: userCredentials, error } = await provider()

		if (error || !userCredentials) {
			return
		}

		trigger(userCredentials)
	}

	return (
		<Flex>
			<Card w="100%" shadow="sm" padding="md" radius="md" withBorder>
				<form>
					<FormHeader title="Business App" subText="Login to continue." />

					<Flex justify={'center'} mt={24}>
						<Button variant="outline" w={'75%'} onClick={() => login(loginWithMicrosoft)}>
							<Flex align={'center'} gap={'4'}>
								<IconBrandWindows size={'24'} />
								<span>Microsoft</span>
							</Flex>
						</Button>
					</Flex>

					<pre>{JSON.stringify(data)}</pre>
				</form>
			</Card>
		</Flex>
	)
}
