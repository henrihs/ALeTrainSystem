package aletrainsystem.models.railroad;

import java.util.HashMap;
import java.util.Map;

import aletrainsystem.enums.PointSwitchConnectorEnum;
import aletrainsystem.models.ConnectorPair;
import aletrainsystem.models.PointSwitchId;
import aletrainsystem.pointswitchcontroller.PointSwitchController;

public class Railroad {
	private Map<PointSwitchId, PointSwitch> pointSwitches;
	private Map<RailLegId, RailLeg> railLegs;
	
	public Railroad(){
		pointSwitches = new HashMap<PointSwitchId, PointSwitch>();
		railLegs = new HashMap<RailLegId, RailLeg>();
	}
	
	public void addPointSwitch(PointSwitch pointSwitch) {
		pointSwitches.put(pointSwitch.getPointSwitchId(), pointSwitch);
	}
	
	public void addRailLeg(RailLeg track){
		railLegs.put(track.getId(), track);
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
	
	public PointSwitch findOrAddPointSwitch(String pointSwitchIdAsString) {
		PointSwitch result = pointSwitches.get(new PointSwitchId(pointSwitchIdAsString));
		if (result == null) {
			result = new PointSwitch(new PointSwitchId(pointSwitchIdAsString));
		}
		return result;
	}
}
