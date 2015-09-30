package aletrainsystem.models.railroad;

import java.io.File;

import aletrainsystem.enums.IntersectionConnectorEnum;
import aletrainsystem.models.PointSwitchId;
import aletrainsystem.models.railroad.Intersection.IntersectionConnector;

public class RailroadFactory {
	
	private Railroad railroad;

	public RailroadFactory(File matrixFile){
		railroad = new Railroad();
	}
	
	private void addTrack(Intersection endpoint1, IntersectionConnectorEnum endpoint1Type, Intersection endpoint2, IntersectionConnectorEnum endpoint2Type, int length){
		addTrack(endpoint1.getConnector(endpoint1Type), endpoint2.getConnector(endpoint2Type), length);
	}
	
	private void addTrack(IntersectionConnector endpoint1, IntersectionConnector endpoint2, int length)	{
		Track track = new Track(endpoint1, endpoint2, length);
		railroad.addTrack(track);
	}
		
	private void addIntersection(int pointSwitchId){
		Intersection intersection = new Intersection(new PointSwitchId(pointSwitchId));
		railroad.addIntersection(intersection);
	}
	
}
