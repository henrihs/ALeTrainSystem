package ntnu.no.aletrainsystem.pointswitch;

import java.util.Date;

import ntnu.no.aletrainsystem.enums.MotorPort;
import ntnu.no.aletrainsystem.enums.SwitchState;
import ntnu.no.aletrainsystem.models.PointSwitchId;

public class PointSwitchOrder {
	private SwitchState switchState;
	private PointSwitchId pointSwitchId;
	private Date timestamp;
	private MotorPort motorPort;
	
	public PointSwitchOrder(PointSwitchId pointSwitchId, SwitchState switchState){
		this.pointSwitchId = pointSwitchId;
		this.switchState = switchState;
		timestamp = new Date();
	}
	
	public PointSwitchId getPointSwitchId(){
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
		if (port == null)
			throw new NullPointerException();
		motorPort = port;
	}
	
}
