package aletrainsystem.models;

import aletrainsystem.enums.MotorPort;

public class MotorPortMapping {

	private MotorPort port;
	private int id;
	
	public MotorPortMapping(MotorPort motorPort, int id){
		this.port = motorPort;
		this.id = id;
	}
	
	public int getPointSwitchId() {
		return id;
	}
	
	public MotorPort getPort() {
		return port;
	}
	
}
