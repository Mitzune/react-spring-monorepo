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
			},
			{
				icon: IconUsers,
				label: 'Customers',
			},
			{
				icon: IconReportAnalytics,
				label: 'Reports',
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
			},
			{
				icon: IconCategory,
				label: 'Categories',
			},
			{
				icon: IconAlertTriangle,
				label: 'Low Stock',
			},
		],
	},
]
