package aletrainsystem.models;

import aletrainsystem.enums.PointSwitchConnectorEnum;
import aletrainsystem.models.railroad.PointSwitchConnector;

public class ConnectorPair extends Pair<PointSwitchConnector, PointSwitchConnector> {

	public ConnectorPair(PointSwitchConnector first, PointSwitchConnector second) {
		super(first, second);
	}
		
	public boolean bothOfType(PointSwitchConnectorEnum connectorType) {
		return first.getConnectorType() == connectorType 
				&& second.getConnectorType() == connectorType;
	}

}
