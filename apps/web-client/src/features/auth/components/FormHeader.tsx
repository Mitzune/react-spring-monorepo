import { Flex, Text, ThemeIcon, Title } from '@mantine/core'
import { IconCashBanknote } from '@tabler/icons-react'

interface FormHeaderType {
	title: string
	subText: string
}

export function FormHeader({ title, subText }: FormHeaderType) {
	return (
		<>
			<Title size={25} ta={'center'}>
				<Flex align={'center'} justify={'center'} gap={'xs'}>
					<ThemeIcon bg={'none'}>
						<IconCashBanknote size={'lg'} />
					</ThemeIcon>
					<span>{title}</span>
				</Flex>
			</Title>
			<Text ta={'center'}>{subText}</Text>
		</>
	)
}
