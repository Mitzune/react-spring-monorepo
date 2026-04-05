import { Button, Card, Flex } from '@mantine/core'
import { IconBrandWindows } from '@tabler/icons-react'
import { Api } from '@utils/Api'
import useSWRMutation from 'swr/mutation'
import { FormHeader } from './components/FormHeader'
import { login, loginWithMicrosoft } from './utils/auth'

export function AuthView() {
	const { trigger, data } = useSWRMutation('/api/auth/Microsoft', Api.post)

	return (
		<Flex>
			<Card w="100%" shadow="sm" padding="md" radius="md" withBorder>
				<form>
					<FormHeader title="Business App" subText="Login to continue." />

					<Flex justify={'center'} mt={24}>
						<Button variant="outline" w={'75%'} onClick={() => login(loginWithMicrosoft, trigger)}>
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
