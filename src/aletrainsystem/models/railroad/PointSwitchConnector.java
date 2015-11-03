package aletrainsystem.models.railroad;

import aletrainsystem.enums.PointSwitchConnectorEnum;
import aletrainsystem.models.Navigation.Destination;

public class PointSwitchConnector implements Destination {
	private final PointSwitch pointSwitch;
	private final PointSwitchConnectorEnum connector;
	private RailLeg connectedRailLeg;

	public PointSwitchConnector(PointSwitch pointSwitch, PointSwitchConnectorEnum connectorType){
		this.pointSwitch = pointSwitch;
		this.connector = connectorType;
		pointSwitch.addConnector(this);
	}
	
	PointSwitchConnector(PointSwitch pointSwitch, PointSwitchConnectorEnum connectorType, RailLeg connectedRailLeg){
		this(pointSwitch, connectorType);
		this.connectedRailLeg = connectedRailLeg;
	}

	public PointSwitch getPointSwitch() {
		return pointSwitch;
	}

	public PointSwitchConnectorEnum getConnectorType() {
		return connector;
	}

	public RailLeg getConnectedRailLeg() {
		return connectedRailLeg;
	}

	public void setConnectedRailLeg(RailLeg connection) {
		this.connectedRailLeg = connection;
	}

	@Override
	public Destination[] getNext(Destination previous) {
		switch (connector) {
		case ENTRY:
			return new Destination[] {pointSwitch.getConnector(PointSwitchConnectorEnum.THROUGH), pointSwitch.getConnector(PointSwitchConnectorEnum.DIVERT)};
		case THROUGH:		
		case DIVERT:
			return new Destination[] {pointSwitch.getConnector(PointSwitchConnectorEnum.ENTRY)};
		default:
			return null;
		}
	}

	@Override
	public int length() {
		return 1;
	}		
}
