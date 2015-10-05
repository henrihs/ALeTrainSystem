package aletrainsystem.models.railroad;

import aletrainsystem.enums.PointSwitchConnectorEnum;
import aletrainsystem.models.PointSwitchId;
import aletrainsystem.models.railroad.PointSwitch.PointSwitchConnector;
import bluebrick4j.conversion.BbmParser;

public class RailroadFactory {
	
	private Railroad railroad;

	public RailroadFactory(){
		railroad = new Railroad();
	}
	
	public Railroad convertBbmFile(String bbmFilePath) {
		Railroad railroad = new Railroad();
		BbmParser.loadMapFromFile("resources/maps/map.bbm");
		
		return railroad;
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
