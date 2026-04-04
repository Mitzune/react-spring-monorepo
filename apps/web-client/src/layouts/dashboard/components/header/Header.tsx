import { Button, Container, Drawer, Flex, Text, ThemeIcon } from '@mantine/core'
import { useDisclosure } from '@mantine/hooks'
import { IconBrandAmongUs, IconMenu2 } from '@tabler/icons-react'
import { useNavigate } from 'react-router'

export function Header() {
	const navigate = useNavigate()
	const [opened, { open, close }] = useDisclosure(false)

	return (
		<>
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

						<Button hiddenFrom="lg" type="button" size="compact-lg" bg="transparent" onClick={open} p={0}>
							<ThemeIcon bg={'transparent'} c={'blue'} size={'xl'}>
								<IconMenu2 />
							</ThemeIcon>
						</Button>
					</Flex>
				</Container>
			</header>

			<Drawer.Root hiddenFrom="lg" opened={opened} onClose={close} size={'xs'} position="right">
				<Drawer.Overlay />
				<Drawer.Content>
					<Drawer.Header>
						<Text size="lg">Navigations</Text>
					</Drawer.Header>
					<Drawer.Body></Drawer.Body>
				</Drawer.Content>
			</Drawer.Root>
		</>
	)
}
