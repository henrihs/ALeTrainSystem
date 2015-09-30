package aletrainsystem.models.railroad;

import java.util.HashMap;
import java.util.Map;

import aletrainsystem.enums.IntersectionConnectorEnum;
import aletrainsystem.models.ConnectorPair;
import aletrainsystem.models.PointSwitchId;

public class Railroad {
	private Map<PointSwitchId, Intersection> intersections;
	private Map<TrackId, Track> tracks;
	
	public Railroad(){
		intersections = new HashMap<PointSwitchId, Intersection>();
		tracks = new HashMap<TrackId, Track>();
	}
	
	public void addIntersection(Intersection intersection) {
		intersections.put(intersection.getPointSwitchId(), intersection);
	}
	
	public void addTrack(Track track){
		tracks.put(track.getId(), track);
	}
	
	public boolean isStationTrack(TrackId trackId) {
		return isStationTrack(findTrack(trackId));
	}
	
	public boolean isStationTrack(Track track){
		if (!tracks.containsKey(track.getId())){
			return false;
		}
		
		ConnectorPair connectors = track.getConnectors();
		if (connectors.getTypeIfIdentical() == IntersectionConnectorEnum.DIVERT){
			TrackId parallelTrackId = new TrackId(
					connectors.first().getIntersection().getConnector(IntersectionConnectorEnum.THROUGH), 
					connectors.second().getIntersection().getConnector(IntersectionConnectorEnum.THROUGH));
			if (findTrack(parallelTrackId) != null){
				return true;
			}
		}
		
		return false;
	}
	
	public Track findTrack(TrackId trackId){
		return tracks.get(trackId);
	}
	
	public Intersection findIntersection(PointSwitchId pointSwitchId) {
		return intersections.get(pointSwitchId);
	}
}
