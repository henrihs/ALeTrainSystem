package aletrainsystem.models.railroad;

import aletrainsystem.models.Navigation.RouteElement;

public class StartLeg extends RailLeg {
	
	private PointSwitchConnector connector;
	
	public StartLeg(PointSwitchConnector connector) {
		super();
		this.connector = connector;
	}
	
	public PointSwitchConnector getConnector() {
		return connector;
	}

	@Override
	public RouteElement[] getNext(RouteElement previous) {
		return new RouteElement[] { connector };
	}

	@Override
	public RailComponent getNextComponent(RailComponent previous, PointSwitchConnector direction) {
		if (direction != connector) {
			return null;
		}
		
		int previousIndex = railBricks.indexOf(previous);
		int nextIndex = previousIndex + 1;
		if (nextIndex >= railBricks.size()) {
			return connector;
		}
		
		return railBricks.get(previousIndex+1);
	}
}
