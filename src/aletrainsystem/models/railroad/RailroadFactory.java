package aletrainsystem.models.railroad;

import java.io.File;

import aletrainsystem.enums.PointSwitchConnectorEnum;
import aletrainsystem.models.PointSwitchId;
import aletrainsystem.models.railroad.PointSwitch.PointSwitchConnector;

public class RailroadFactory {
	
	private Railroad railroad;

	public RailroadFactory(File bbmFile){
		railroad = new Railroad();
	}
	
	private void addRailLeg(PointSwitch endpoint1, PointSwitchConnectorEnum endpoint1Type, PointSwitch endpoint2, PointSwitchConnectorEnum endpoint2Type, int length){
		addRailLeg(endpoint1.getConnector(endpoint1Type), endpoint2.getConnector(endpoint2Type), length);
	}
	
	private void addRailLeg(PointSwitchConnector endpoint1, PointSwitchConnector endpoint2, int length)	{
		RailLeg railLeg = new RailLeg(endpoint1, endpoint2, length);
		railroad.addRailLeg(railLeg);
	}
		
	private void addIntersection(int pointSwitchId){
		PointSwitch intersection = new PointSwitch(new PointSwitchId(pointSwitchId));
		railroad.addIntersection(intersection);
	}
	
}
