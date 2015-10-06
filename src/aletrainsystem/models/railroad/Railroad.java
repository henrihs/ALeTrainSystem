package aletrainsystem.models.railroad;

import java.util.HashMap;
import java.util.Map;

import aletrainsystem.enums.PointSwitchConnectorEnum;
import aletrainsystem.models.ConnectorPair;
import aletrainsystem.models.PointSwitchId;

public class Railroad {
	private Map<Integer, PointSwitch> pointSwitches;
	private Map<String, RailLeg> railLegs;
	
	protected Railroad(){
		pointSwitches = new HashMap<Integer, PointSwitch>();
		railLegs = new HashMap<String, RailLeg>();
	}
	
	protected void addPointSwitch(PointSwitch pointSwitch) {
		pointSwitches.put(pointSwitch.getPointSwitchId().value(), pointSwitch);
	}
	
	protected void addRailLeg(RailLeg track){
		railLegs.put(track.getId().value(), track);
		
	}
	
	public boolean isStationTrack(RailLegId trackId) {
		return isStationTrack(findRailLeg(trackId));
	}
	
	public boolean isStationTrack(RailLeg track){
		if (!railLegs.containsKey(track.getId())){
			return false;
		}
		
		ConnectorPair connectors = track.getConnectors();
		if (connectors.getTypeIfIdentical() == PointSwitchConnectorEnum.DIVERT){
			RailLegId parallelTrackId = new RailLegId(
					connectors.first().getIntersection().getConnector(PointSwitchConnectorEnum.THROUGH), 
					connectors.second().getIntersection().getConnector(PointSwitchConnectorEnum.THROUGH));
			if (findRailLeg(parallelTrackId) != null){
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
	
	public PointSwitch findOrAddPointSwitch(int pointSwitchId) {
		PointSwitch result = pointSwitches.get(pointSwitchId);
		if (result == null) {
			result = new PointSwitch(new PointSwitchId(pointSwitchId));
			pointSwitches.put(pointSwitchId, result);
		}
		return result;
	}
}
