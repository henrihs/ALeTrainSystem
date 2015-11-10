package aletrainsystem.models.railroad;

import aletrainsystem.models.navigation.RouteElement;

public class StartLeg extends RailLeg {
	
	private PointConnector connector;
	
	public StartLeg(PointConnector connector) {
		super();
		this.connector = connector;
	}
	
	public PointConnector getConnector() {
		return connector;
	}

	@Override
	public RouteElement[] getNext(RouteElement previous) {
		return new RouteElement[] { connector };
	}

	@Override
	public RailComponent getNextComponent(RailComponent previous, PointConnector direction) {
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
	
//	public RailComponent[] getStartOfLeg(int components) {
//		if (components > railBricks.size())
//			throw new IllegalArgumentException("This StartLeg has too few components to accomodate the vessel");
//		return new RailComponent[] { (RailComponent) railBricks.subList(0, components - 1) };
//	}

	@Override
	public String id() {
		return connector.id().toString().concat(".");
	}
}
