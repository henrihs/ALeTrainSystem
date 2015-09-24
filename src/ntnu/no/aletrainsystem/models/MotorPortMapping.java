package ntnu.no.aletrainsystem.models;

import ntnu.no.aletrainsystem.enums.MotorPort;

public class MotorPortMapping {

	private MotorPort port;
	private PointSwitchId id;
	
	public MotorPortMapping(MotorPort motorPort, PointSwitchId id){
		this.port = motorPort;
		this.id = id;
	}
	
	public PointSwitchId getId() {
		return id;
	}
	
	public MotorPort getPort() {
		return port;
	}
	
}
