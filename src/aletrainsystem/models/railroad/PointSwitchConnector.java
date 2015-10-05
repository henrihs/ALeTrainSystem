package aletrainsystem.models.railroad;

import aletrainsystem.enums.PointSwitchConnectorEnum;

public class PointSwitchConnector {
	private final PointSwitch pointSwitch;
	private final PointSwitchConnectorEnum connector;
	private RailLeg connection;

	public PointSwitchConnector(PointSwitch intersection, PointSwitchConnectorEnum connectorType){
		this.pointSwitch = intersection;
		this.connector = connectorType;
		intersection.addConnector(this);
	}
	
	PointSwitchConnector(PointSwitch intersection, PointSwitchConnectorEnum connectorType, RailLeg connection){
		this(intersection, connectorType);
		this.connection = connection;
	}

	public PointSwitch getIntersection() {
		return pointSwitch;
	}

	public PointSwitchConnectorEnum getConnectorType() {
		return connector;
	}

	public RailLeg getConnection() {
		return connection;
	}

	public void setConnection(RailLeg connection) {
		this.connection = connection;
	}		
}
