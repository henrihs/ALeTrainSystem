package aletrainsystem.models;

import aletrainsystem.enums.IntersectionConnectorEnum;
import aletrainsystem.models.railroad.Intersection.IntersectionConnector;

public class ConnectorPair extends Pair<IntersectionConnector> {

	public ConnectorPair(IntersectionConnector first, IntersectionConnector second) {
		super(first, second);
	}
	
	public IntersectionConnectorEnum getTypeIfIdentical() {
		if (first.getConnectorType() == second.getConnectorType()) {
			return first.getConnectorType();
		}
		
		return null;
	}

}
