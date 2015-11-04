package aletrainsystem.models.railroad;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import aletrainsystem.enums.PointConnectorEnum;
import aletrainsystem.models.RailComponentId;
import aletrainsystem.models.Navigation.RouteElement;
import aletrainsystem.models.Navigation.Route;

public class Railroad {
	
	public static final String PROPERTYNAME = "MAP_FILE";
	
	private Map<Long, Point> pointSwitches;
	private Map<String, RegularLeg> railLegs;
	private Set<PointConnector> connectedPointSwitchConnectors;
	private StartLeg railSystemEntryPoint;
	
	protected Railroad(){
		pointSwitches = new HashMap<>();
		railLegs = new HashMap<>();
		connectedPointSwitchConnectors = new HashSet<>();
	}
	
	Map<String, RegularLeg> getRailLegs(){
		return railLegs;
	}
	
	Map<Long, Point> getPointSwitches(){
		return pointSwitches;
	}
	
	protected void addPointSwitch(Point pointSwitch) {
		pointSwitches.put(pointSwitch.id().value(), pointSwitch);
	}
	
	protected void addRailLeg(RegularLeg railLeg){
		railLegs.put(railLeg.getId().value(), railLeg);
		railLeg.getConnectors().forEach(c -> connectedPointSwitchConnectors.add(c));
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
		if (railLeg == null || !railLegs.containsKey(railLeg.getId().value())){
			return false;
		}
		
		ConnectorPair connectors = railLeg.getConnectors();
		if (connectors.bothOfType(PointConnectorEnum.DIVERT)){
			RailLegId parallelRailLegId = new RailLegId(
					connectors.first().pointSwitch().getConnector(PointConnectorEnum.THROUGH), 
					connectors.second().pointSwitch().getConnector(PointConnectorEnum.THROUGH));
			if (findRailLeg(parallelRailLegId) != null){
				return true;
			}
		}
		
		return false;
	}
	
	public RegularLeg findRailLeg(String railLegId) {
		return railLegs.get(railLegId);
	}
	
	public RegularLeg findRailLeg(RailLegId railLegId){
		return findRailLeg(railLegId.value());
	}
	
	protected boolean hasRailLegWithConnector(PointConnector connector) {
		return connectedPointSwitchConnectors.contains(connector);
	}
	
	public Point findPointSwitch(String pointSwitchId) {
		return pointSwitches.get(pointSwitchId);
	}
	
	public Point findPointSwitch(RailComponentId pointSwitchId) {
		return pointSwitches.get(pointSwitchId.value());
	}
	
	public Point findOrAddPointSwitch(long pointSwitchId) {
		Point result = pointSwitches.get(pointSwitchId);
		if (result == null) {
			result = new Point(new RailComponentId(pointSwitchId));
			pointSwitches.put(pointSwitchId, result);
		}
		return result;
	}
}
