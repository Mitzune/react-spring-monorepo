import { HomeView } from '@features/home/HomeView'
import { PostCreateView } from '@features/post/create/PostCreateView'
import { PostListView } from '@features/post/list/PostListView'
import { DashboardLayout } from '@layouts/dashboard/DashboardLayout'
import { createBrowserRouter } from 'react-router'

const router = createBrowserRouter([
	{
		Component: DashboardLayout,
		children: [
			{
				index: true,
				Component: HomeView,
			},
		],
	},
	{
		path: 'posts',
		Component: DashboardLayout,
		children: [
			{
				path: 'create',
				Component: PostCreateView,
			},
			{
				path: 'list',
				Component: PostListView,
			},
		],
	},
])

export { router }
