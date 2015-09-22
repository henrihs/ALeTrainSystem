package ntnu.no.aletrainsystem.pointswitch;

import no.ntnu.item.arctis.runtime.Block;

public class PointSwitch extends Block {

	public ntnu.no.aletrainsystem.pointswitch.SwitchState finalState;
	public ntnu.no.aletrainsystem.pointswitch.SwitchState currentState;
	public ntnu.no.aletrainsystem.pointswitch.PointSwitchOrder currentOrder;
	
	public static String getAlias(String s){
		return s;
	}
	
	public static String getAlias(PointSwitchOrder order){
		return order.getMotorPort();
	}
	
	public boolean isAlreadyInFinalState(){
		return finalState == currentState;
	}

	public SwitchState getState(PointSwitchOrder order) {
		return order.getSwitchState();
	}
	
}
