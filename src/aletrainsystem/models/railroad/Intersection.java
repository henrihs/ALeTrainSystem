package aletrainsystem.models.railroad;

import java.util.HashMap;
import java.util.Map;

import aletrainsystem.enums.IntersectionConnectorEnum;
import aletrainsystem.models.PointSwitchId;

public class Intersection {
	private Map<IntersectionConnectorEnum, IntersectionConnector> connectors;
	private PointSwitchId pointSwitchId;
	
	public Intersection(PointSwitchId pointSwitchId){
		this.pointSwitchId = pointSwitchId;
		connectors = new HashMap<IntersectionConnectorEnum, IntersectionConnector>();
		for (IntersectionConnectorEnum connectorType : IntersectionConnectorEnum.values()) {
			addConnector(new IntersectionConnector(this, connectorType));
		}
	}
	
	public PointSwitchId getPointSwitchId(){
		return pointSwitchId;
	}
	
	
	public Map<IntersectionConnectorEnum, IntersectionConnector> getConnectors() {
		return connectors;
	}
	
	public IntersectionConnector getConnector(IntersectionConnectorEnum type){
		return connectors.get(type);
	}

	public void addConnector(IntersectionConnector connector) {
		connectors.put(connector.getConnectorType(), connector);
	}

	@Override
	public boolean equals(Object other){
		return this.pointSwitchId.equals(((Intersection)other).getPointSwitchId());
	}
	
	public class IntersectionConnector {
		private final Intersection intersection;
		private final IntersectionConnectorEnum connector;
		private Track connection;
	
		public IntersectionConnector(Intersection intersection, IntersectionConnectorEnum connectorType){
			this.intersection = intersection;
			this.connector = connectorType;
			intersection.addConnector(this);
		}
		
		IntersectionConnector(Intersection intersection, IntersectionConnectorEnum connectorType, Track connection){
			this(intersection, connectorType);
			this.connection = connection;
		}

		public Intersection getIntersection() {
			return intersection;
		}

		public IntersectionConnectorEnum getConnectorType() {
			return connector;
		}

		public Track getConnection() {
			return connection;
		}

		public void setConnection(Track connection) {
			this.connection = connection;
		}		
	}
}
