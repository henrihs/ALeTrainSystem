package aletrainsystem.models;

import java.util.Date;

import aletrainsystem.enums.MotorPort;
import aletrainsystem.enums.SwitchState;

public class PointSwitchOrder {
	private SwitchState switchState;
	private int pointSwitchId;
	private Date timestamp;
	private MotorPort motorPort;
	
	public PointSwitchOrder(int pointSwitchId, SwitchState switchState){
		this.pointSwitchId = pointSwitchId;
		this.switchState = switchState;
		timestamp = new Date();
	}
	
	public int getPointSwitchId(){
		return pointSwitchId;
	}
	
	public SwitchState getSwitchState(){
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
