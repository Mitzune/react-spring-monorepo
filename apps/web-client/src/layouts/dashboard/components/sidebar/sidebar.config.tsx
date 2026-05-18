import {
	IconAlertTriangle,
	IconCategory,
	IconDashboard,
	IconPackage,
	IconReportAnalytics,
	IconUsers,
} from '@tabler/icons-react'

export const sidebarLinks = [
	{
		label: 'Business',
		isDefaultOpen: true,
		items: [
			{
				icon: IconDashboard,
				label: 'Dashboard',
				path: '/',
			},
			{
				icon: IconUsers,
				label: 'Customers',
				path: '/customers',
			},
			{
				icon: IconReportAnalytics,
				label: 'Reports',
				path: '/reports',
			},
		],
	},
	{
		label: 'Inventories',
		isDefaultOpen: false,
		items: [
			{
				icon: IconPackage,
				label: 'All Items',
				path: '/reports',
			},
			{
				icon: IconCategory,
				label: 'Categories',
				path: '/reports',
			},
			{
				icon: IconAlertTriangle,
				label: 'Low Stock',
				path: '/reports',
			},
		],
	},
]
