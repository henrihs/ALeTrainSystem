package ntnu.no.aletrainsystem.pointswitch;

import java.util.Date;

public class PointSwitchOrder {
	private SwitchState switchState;
	private int pointSwitchId;
	private Date timestamp;
	private String motorPort;
	
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
	
	public String getMotorPort(){
		return motorPort;
	}
	
	public Date getTimestamp(){
		return timestamp;
	}
	
	protected void setMotorPort(String s){
		motorPort = s;
	}
	
}
