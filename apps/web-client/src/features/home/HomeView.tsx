import { AreaChart, BarChart } from '@mantine/charts'
import { Card, SimpleGrid, Text, Title } from '@mantine/core'
import { areaData, barData } from './data'

export function HomeView() {
	return (
		<>
			<AreaChart
				h={300}
				data={areaData}
				dataKey="date"
				series={[
					{ name: 'Apples', color: 'indigo.6' },
					{ name: 'Oranges', color: 'blue.6' },
					{ name: 'Tomatoes', color: 'teal.6' },
				]}
				curveType="linear"
			/>

			<SimpleGrid cols={{ base: 1, md: 2 }}>
				<BarChart
					h={300}
					data={barData}
					dataKey="month"
					series={[
						{ name: 'Smartphones', color: 'violet.6' },
						{ name: 'Laptops', color: 'blue.6' },
						{ name: 'Tablets', color: 'teal.6' },
					]}
					tickLine="y"
				/>

				<Card withBorder>
					<Title order={4}>System Status</Title>
					<Text size="sm" c="green" mt="xs">
						All systems operational
					</Text>
				</Card>

				<Card withBorder>
					<Title order={4}>System Status</Title>
					<Text size="sm" c="orange" mt="xs">
						All issue's are resolved
					</Text>
				</Card>

				<Card withBorder>
					<Title order={4}>Recent Activity</Title>
					<Text size="sm" c="dimmed" mt="xs">
						No recent activity yet.
					</Text>
				</Card>
			</SimpleGrid>
		</>
	)
}
