package aletrainsystem.models;

import aletrainsystem.enums.MotorPort;

public class MotorPortMapping {

	private MotorPort port;
	private PointSwitchId pointSwitchId;
	
	public MotorPortMapping(MotorPort motorPort, PointSwitchId id){
		this.port = motorPort;
		this.pointSwitchId = id;
	}
	
	public PointSwitchId getPointSwitchId() {
		return pointSwitchId;
	}
	
	public MotorPort getPort() {
		return port;
	}
	
}
