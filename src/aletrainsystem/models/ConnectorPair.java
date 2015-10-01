package aletrainsystem.models;

import aletrainsystem.enums.PointSwitchConnectorEnum;
import aletrainsystem.models.railroad.PointSwitch.PointSwitchConnector;

public class ConnectorPair extends Pair<PointSwitchConnector, PointSwitchConnector> {

	public ConnectorPair(PointSwitchConnector first, PointSwitchConnector second) {
		super(first, second);
	}
	
	public PointSwitchConnectorEnum getTypeIfIdentical() {
		if (first.getConnectorType() == second.getConnectorType()) {
			return first.getConnectorType();
		}
		
		return null;
	}

}
