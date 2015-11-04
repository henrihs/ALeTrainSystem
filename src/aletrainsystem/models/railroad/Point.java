package aletrainsystem.models.railroad;

import java.util.HashMap;
import java.util.Map;

import aletrainsystem.enums.PointConnectorEnum;
import aletrainsystem.models.RailComponentId;

public class Point {
	private Map<PointConnectorEnum, PointConnector> connectors;
	private RailComponentId pointSwitchId;
	
	public Point(RailComponentId pointSwitchId){
		this.pointSwitchId = pointSwitchId;
		connectors = new HashMap<PointConnectorEnum, PointConnector>();
		for (PointConnectorEnum connectorType : PointConnectorEnum.values()) {
			addConnector(new PointConnector(this, connectorType));
		}
	}
	
	protected RailComponentId id(){
		return pointSwitchId;
	}
	
	
	public Map<PointConnectorEnum, PointConnector> getConnectors() {
		return connectors;
	}
	
	public PointConnector getConnector(PointConnectorEnum type){
		return connectors.get(type);
	}

	public void addConnector(PointConnector connector) {
		connectors.put(connector.getType(), connector);
	}

	@Override
	public boolean equals(Object other){
		return this.pointSwitchId.equals(((Point)other).id());
	}
}
