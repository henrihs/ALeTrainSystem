package aletrainsystem.models.railroad;

import aletrainsystem.enums.TrackStatus;
import aletrainsystem.models.navigation.RouteElement;
import aletrainsystem.models.railroad.PointConnector;

public class RegularLeg extends RailLeg {
	
	private ConnectorPair connectors;
	private RailLegId trackId;
	private TrackStatus status;
			
	public RegularLeg(PointConnector connector1, PointConnector connector2){
		super();
		addConnectors(connector1, connector2);
		trackId = new RailLegId(connector1, connector2);
		status = TrackStatus.NORMAL;
		
	}
	
	public RailLegId id() {
		return trackId;
	}
		
	public ConnectorPair getConnectors(){
		return connectors;
	}
	
	private void addConnectors(PointConnector connector1, PointConnector connector2){
		connectors = new ConnectorPair(connector1, connector2);
		if (connector1 != null) {
			connector1.setConnectedRailLeg(this);
		}
		
		if (connector2 != null) {
			connector2.setConnectedRailLeg(this);
		}
	}
	
	
	
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof RegularLeg)) {
			return false;
		}
		
		RegularLeg track = (RegularLeg) other;
		
		return (trackId.equals(track.trackId) && length() == track.length());
	}

	public TrackStatus getStatus() {
		return status;
	}

	public void setStatus(TrackStatus status) {
		this.status = status;
	}

	
	
	public RailComponent getNextComponent(RailComponent previous, PointConnector direction) {
		if (!railBricks.contains(previous)) {
			if (direction == connectors.first()) {
				return railBricks.get(railBricks.size() - 1);
			}
			else if (direction == connectors.second()) {
				return railBricks.get(0);
			}
		}
		
		int previousIndex = railBricks.indexOf(previous);
		int relativeDirection = getRelativeDirection(direction);
		int nextIndex = previousIndex + relativeDirection;
		if (nextIndex >= 0 && nextIndex < railBricks.size()) {
			return railBricks.get(nextIndex);			
		}
		else if (relativeDirection > 0) {
			return connectors.second();
		}
		else if (relativeDirection < 0) {
			return connectors.first();
		}
		
		throw new IllegalArgumentException("Could not relate to PointSwitch with Id " + direction.toString());
	}
	
	public PointConnector getConnector(Point pointSwitch) {
		if (connectors.first().point().equals(pointSwitch)) {
			return connectors.first();
		}
		else if (connectors.second().point().equals(pointSwitch)) {
			return connectors.second();
		}
		return null;
	}
	
	@Override
	public RouteElement[] getNext(RouteElement previous) {
		if (previous == connectors.first()) {
			return new RouteElement[] {connectors.second()};
		}
		else if (previous == connectors.second()) {
			return new RouteElement[] {connectors.first()};
		}
		else
			return null;
	}
	
	private int getRelativeDirection(PointConnector direction){
		if (connectors.first().equals(direction)){
			return -1;
		}
		else if (connectors.second().equals(direction)){
			return 1;
		}
		
		return 0;
	}

	public PointConnector getOppositeConnector(PointConnector connector){
		return getOppositeConnector(connector.point());
	}
	
	public PointConnector getOppositeConnector(Point pointSwitch) {
		if (pointSwitch == connectors.first().point()) {
			return connectors.second();
		}
		else if (pointSwitch == connectors.second().point()) {
			return connectors.first();
		}
		return null;
	}
}
