package aletrainsystem.models;

import java.util.Date;

import aletrainsystem.enums.MotorPort;
import aletrainsystem.enums.IntersectionConnectorEnum;

public class PointSwitchOrder {
	private IntersectionConnectorEnum switchState;
	private PointSwitchId pointSwitchId;
	private Date timestamp;
	private MotorPort motorPort;
	
	public PointSwitchOrder(PointSwitchId pointSwitchId, IntersectionConnectorEnum switchState){
		this.pointSwitchId = pointSwitchId;
		this.switchState = switchState;
		timestamp = new Date();
	}
	
	public PointSwitchId getPointSwitchId(){
		return pointSwitchId;
	}
	
	public IntersectionConnectorEnum getSwitchState(){
		return switchState;
	}
	
	public MotorPort getMotorPort(){
		return motorPort;
	}
	
	public Date getTimestamp(){
		return timestamp;
	}
	
	public void setMotorPort(MotorPort port){
		motorPort = port;
	}
	
}
