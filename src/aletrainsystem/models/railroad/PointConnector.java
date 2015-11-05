package aletrainsystem.models.railroad;

import aletrainsystem.enums.PointConnectorEnum;
import aletrainsystem.models.RailComponentId;
import aletrainsystem.models.Navigation.RouteElement;

public class PointConnector extends RouteElement implements RailComponent {
	private final Point point;
	private final PointConnectorEnum connector;
	private RailLeg connectedRailLeg;

	public PointConnector(Point point, PointConnectorEnum connectorType){
		this.point = point;
		this.connector = connectorType;
		point.addConnector(this);
	}
	
	PointConnector(Point pointSwitch, PointConnectorEnum connectorType, RailLeg connectedRailLeg){
		this(pointSwitch, connectorType);
		this.connectedRailLeg = connectedRailLeg;
	}

	public Point point() {
		return point;
	}

	public PointConnectorEnum getType() {
		return connector;
	}

	public RailLeg getConnectedRailLeg() {
		return connectedRailLeg;
	}

	public void setConnectedRailLeg(RegularLeg connection) {
		this.connectedRailLeg = connection;
	}

	@Override
	public RouteElement[] getNext(RouteElement previous) {
		switch (connector) {
		case ENTRY:
			return new RouteElement[] {point.getConnector(PointConnectorEnum.THROUGH), point.getConnector(PointConnectorEnum.DIVERT)};
		case THROUGH:		
		case DIVERT:
			return new RouteElement[] {point.getConnector(PointConnectorEnum.ENTRY)};
		default:
			return null;
		}
	}

	@Override
	public int length() {
		return 1;
	}

	@Override
	public RailComponentId id() {
		return point.id();
	}		
}
