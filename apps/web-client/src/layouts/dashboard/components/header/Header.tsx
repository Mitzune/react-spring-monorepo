import { Button, Container, Flex, Text, ThemeIcon } from '@mantine/core'
import { IconBrandAmongUs } from '@tabler/icons-react'
import { useNavigate } from 'react-router'

export function Header() {
	const navigate = useNavigate()
	return (
		<header>
			<Container size={1400} p={12}>
				<Flex align={'center'} justify={'space-between'}>
					<a onClick={() => navigate('/')}>
						<Flex gap={'sm'} align={'center'}>
							<ThemeIcon>
								<IconBrandAmongUs />
							</ThemeIcon>

							<Text c={'blue'} fw={800} size="xl">
								Cool company
							</Text>
						</Flex>
					</a>

					<Flex gap={'sm'}>
						<Button variant="subtle" onClick={() => navigate('/posts/create')}>
							Create Post
						</Button>

						<Button variant="subtle" onClick={() => navigate('/posts/list')}>
							View Post
						</Button>
					</Flex>
				</Flex>
			</Container>
		</header>
	)
}
