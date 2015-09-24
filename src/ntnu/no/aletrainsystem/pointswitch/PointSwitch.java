package ntnu.no.aletrainsystem.pointswitch;

import no.ntnu.item.arctis.runtime.Block;
import ntnu.no.aletrainsystem.enums.SwitchState;
import ntnu.no.aletrainsystem.enums.MotorPort;

public class PointSwitch extends Block {

	public ntnu.no.aletrainsystem.enums.SwitchState finalState;
	public ntnu.no.aletrainsystem.pointswitch.PointSwitchOrder currentOrder;
	public ntnu.no.aletrainsystem.enums.SwitchState currentState;
	
	public static String getAlias(MotorPort port){
		return port.name();
	}
	
	public static String getAlias(PointSwitchOrder order){
		return order.getMotorPort().name();
	}
	
	public boolean isAlreadyInFinalState(){
		return finalState == currentState;
	}

	public SwitchState getState(PointSwitchOrder order) {
		return order.getSwitchState();
	}
	
}
