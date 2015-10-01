package aletrainsystem.models.railroad;

import java.util.HashMap;
import java.util.Map;

import aletrainsystem.enums.PointSwitchConnectorEnum;
import aletrainsystem.models.PointSwitchId;

public class PointSwitch {
	private Map<PointSwitchConnectorEnum, PointSwitchConnector> connectors;
	private PointSwitchId pointSwitchId;
	
	public PointSwitch(PointSwitchId pointSwitchId){
		this.pointSwitchId = pointSwitchId;
		connectors = new HashMap<PointSwitchConnectorEnum, PointSwitchConnector>();
		for (PointSwitchConnectorEnum connectorType : PointSwitchConnectorEnum.values()) {
			addConnector(new PointSwitchConnector(this, connectorType));
		}
	}
	
	public PointSwitchId getPointSwitchId(){
		return pointSwitchId;
	}
	
	
	public Map<PointSwitchConnectorEnum, PointSwitchConnector> getConnectors() {
		return connectors;
	}
	
	public PointSwitchConnector getConnector(PointSwitchConnectorEnum type){
		return connectors.get(type);
	}

	public void addConnector(PointSwitchConnector connector) {
		connectors.put(connector.getConnectorType(), connector);
	}

	@Override
	public boolean equals(Object other){
		return this.pointSwitchId.equals(((PointSwitch)other).getPointSwitchId());
	}
	
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
}
