package aletrainsystem.models.railroad;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import aletrainsystem.enums.PointConnectorEnum;
import aletrainsystem.models.RailComponentId;

public class Railroad implements IRailroad {

	private Map<Long, Point> points;
	private Map<String, RegularLeg> legs;
	private Set<PointConnector> connectedPointConnectors;
	private StartLeg railSystemEntryPoint;

	protected Railroad(){
		points = new HashMap<>();
		legs = new HashMap<>();
		connectedPointConnectors = new HashSet<>();
	}

	Map<String, RegularLeg> getRailLegs(){
		return legs;
	}

	Map<Long, Point> getPointSwitches(){
		return points;
	}

	protected void addPointSwitch(Point pointSwitch) {
		points.put(pointSwitch.id().value(), pointSwitch);
	}

	protected void addRailLeg(RegularLeg railLeg){
		legs.put(railLeg.getId().value(), railLeg);
		railLeg.getConnectors().forEach(c -> connectedPointConnectors.add(c));
	}

	protected void setRailSystemStartLeg(StartLeg railSystemEntryPoint) {
		this.railSystemEntryPoint = railSystemEntryPoint;
	}

	public StartLeg getRailSystemStartLeg() {
		return railSystemEntryPoint;
	}

	public boolean isStation(RailLegId railLegId) {
		return isStation(railLegId.value());
	}

	public boolean isStation(String railLegId) {
		return isStation(findRailLeg(railLegId));
	}

	public boolean isStation(RegularLeg railLeg){
		if (railLeg == null || !legs.containsKey(railLeg.getId().value())){
			return false;
		}

		ConnectorPair connectors = railLeg.getConnectors();
		if (connectors.bothOfType(PointConnectorEnum.DIVERT)){
			RailLegId parallelRailLegId = new RailLegId(
					connectors.first().point().getConnector(PointConnectorEnum.THROUGH), 
					connectors.second().point().getConnector(PointConnectorEnum.THROUGH));
			if (findRailLeg(parallelRailLegId) != null){
				return true;
			}
		}
		else if (connectors.bothOfType(PointConnectorEnum.THROUGH)) {
			RailLegId parallelRailLegId = new RailLegId(
					connectors.first().point().getConnector(PointConnectorEnum.DIVERT), 
					connectors.second().point().getConnector(PointConnectorEnum.DIVERT));
			if (findRailLeg(parallelRailLegId) != null){
				return true;
			}
		}

		return false;
	}

	public RegularLeg findRailLeg(String railLegId) {
		return legs.get(railLegId);
	}

	public RegularLeg findRailLeg(RailLegId railLegId){
		return findRailLeg(railLegId.value());
	}

	protected boolean hasRailLegWithConnector(PointConnector connector) {
		return connectedPointConnectors.contains(connector);
	}

	public Point findPointSwitch(String pointSwitchId) {
		return points.get(pointSwitchId);
	}

	public Point findPointSwitch(RailComponentId pointSwitchId) {
		return points.get(pointSwitchId.value());
	}

	public Point findOrAddPoint(long pointSwitchId) {
		Point result = points.get(pointSwitchId);
		if (result == null) {
			result = new Point(new RailComponentId(pointSwitchId));
			points.put(pointSwitchId, result);
		}
		return result;
	}
}
