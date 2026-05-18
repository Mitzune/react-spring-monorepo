import { Card, CardContent, CardHeader } from '@app/components/ui/card'
import { cn } from '@app/lib/utils'
import { Container } from '@features/base/components/container'

import { SampleAreaChart, SamplePieChart, TEST_CARD_DATA } from './sample/SampleCharts'

export function DashboardView() {
	return (
		<Container className="flex flex-col gap-4">
			<div className="grid grid-cols-2 gap-4 md:grid-cols-4">
				{TEST_CARD_DATA.map((card, index) => (
					<Card
						key={`${card.label}-${index}`}
						className={cn(
							card.isPrimary
								? 'bg-primary text-primary-foreground'
								: 'bg-secondary text-secondary-foreground',
						)}
					>
						<CardHeader className="flex items-center justify-between">
							<span> {card.label} </span>
							<card.icon />
						</CardHeader>

						<CardContent className="flex items-center justify-between">
							<span className="font-semibold">{card.value}</span>

							<span className="text-xs">+12.4%</span>
						</CardContent>
					</Card>
				))}
			</div>

			<SampleAreaChart />

			<div className="flex flex-col gap-4 md:flex-row md:items-center md:justify-center">
				<SamplePieChart />

				<SamplePieChart />
			</div>
		</Container>
	)
}
