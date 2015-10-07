package aletrainsystem.models.railroad;

import java.util.HashMap;
import java.util.Map;

import aletrainsystem.enums.PointSwitchConnectorEnum;
import aletrainsystem.models.ConnectorPair;
import aletrainsystem.models.PointSwitchId;

public class Railroad {
	private Map<Long, PointSwitch> pointSwitches;
	private Map<String, RailLeg> railLegs;
	
	protected Railroad(){
		pointSwitches = new HashMap<Long, PointSwitch>();
		railLegs = new HashMap<String, RailLeg>();
	}
	
	protected void addPointSwitch(PointSwitch pointSwitch) {
		pointSwitches.put(pointSwitch.getPointSwitchId().value(), pointSwitch);
	}
	
	protected void addRailLeg(RailLeg track){
		railLegs.put(track.getId().value(), track);
		
	}
	
	public boolean isStation(String railLegId) {
		return isStation(new RailLegId(railLegId));
	}
	
	public boolean isStation(RailLegId railLegId) {
		return isStation(findRailLeg(railLegId));
	}
	
	public boolean isStation(RailLeg railLeg){
		if (railLeg == null || !railLegs.containsKey(railLeg.getId())){
			return false;
		}
		
		ConnectorPair connectors = railLeg.getConnectors();
		if (connectors.bothOfType(PointSwitchConnectorEnum.DIVERT)){
			RailLegId parallelRailLegId = new RailLegId(
					connectors.first().getIntersection().getConnector(PointSwitchConnectorEnum.THROUGH), 
					connectors.second().getIntersection().getConnector(PointSwitchConnectorEnum.THROUGH));
			if (findRailLeg(parallelRailLegId) != null){
				return true;
			}
		}
		
		return false;
	}
	
	public RailLeg findRailLeg(RailLegId trackId){
		return railLegs.get(trackId);
	}
	
	public PointSwitch findPointSwitch(PointSwitchId pointSwitchId) {
		return pointSwitches.get(pointSwitchId);
	}
	
	public PointSwitch findOrAddPointSwitch(long pointSwitchId) {
		PointSwitch result = pointSwitches.get(pointSwitchId);
		if (result == null) {
			result = new PointSwitch(new PointSwitchId(pointSwitchId));
			pointSwitches.put(pointSwitchId, result);
		}
		return result;
	}
}
