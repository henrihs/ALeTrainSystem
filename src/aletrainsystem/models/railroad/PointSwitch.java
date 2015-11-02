package aletrainsystem.models.railroad;

import java.util.HashMap;
import java.util.Map;

import aletrainsystem.enums.PointSwitchConnectorEnum;
import aletrainsystem.models.RailPartId;

public class PointSwitch implements RailPart {
	private Map<PointSwitchConnectorEnum, PointSwitchConnector> connectors;
	private RailPartId pointSwitchId;
	
	public PointSwitch(RailPartId pointSwitchId){
		this.pointSwitchId = pointSwitchId;
		connectors = new HashMap<PointSwitchConnectorEnum, PointSwitchConnector>();
		for (PointSwitchConnectorEnum connectorType : PointSwitchConnectorEnum.values()) {
			addConnector(new PointSwitchConnector(this, connectorType));
		}
	}
	
	public RailPartId id(){
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
		return this.pointSwitchId.equals(((PointSwitch)other).id());
	}
}
