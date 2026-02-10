import type { TypePost } from '../types/Post'
import { Card, Flex, Text } from '@mantine/core'
import { notifications } from '@mantine/notifications'
import { IconUser } from '@tabler/icons-react'
import Api from '@utils/Api'
import { useEffect, useState } from 'react'

export function PostListView() {
	const [posts, setPosts] = useState<TypePost[]>([])

	useEffect(() => {
		async function fetchPosts() {
			try {
				const response = await Api.get({}, { path: '/posts' })

				if (response.length > 0) {
					setPosts(response)
				}
			} catch {
				notifications.show({ color: 'red', title: 'Something went wrong', message: 'Api Error!' })
			}
		}

		fetchPosts()
	}, [])

	return (
		<Flex direction={'column'} gap={'lg'}>
			{posts.length > 0 ? (
				posts.map((post, index) => (
					<Card key={`post-${post.authorName}-${index}`} shadow="sm" padding="lg">
						<Flex align={'center'} gap={'md'}>
							<IconUser />

							<Flex w={'100%'} direction={'column'}>
								<Text size="lg">{post.authorName}</Text>
								<Text size="sm">{post.message}</Text>
							</Flex>
						</Flex>
					</Card>
				))
			) : (
				<Text>No posts yet made</Text>
			)}
		</Flex>
	)
}
