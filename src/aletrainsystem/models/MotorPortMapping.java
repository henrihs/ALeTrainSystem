package aletrainsystem.models;

import aletrainsystem.enums.MotorPort;

public class MotorPortMapping {

	private MotorPort port;
	private RailPartId pointSwitchId;
	
	public MotorPortMapping(MotorPort motorPort, RailPartId id){
		this.port = motorPort;
		this.pointSwitchId = id;
	}
	
	public RailPartId getPointSwitchId() {
		return pointSwitchId;
	}
	
	public MotorPort getPort() {
		return port;
	}
	
}
