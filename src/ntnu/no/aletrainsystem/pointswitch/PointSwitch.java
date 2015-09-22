package ntnu.no.aletrainsystem.pointswitch;

import no.ntnu.item.arctis.runtime.Block;

public class PointSwitch extends Block {

	public ntnu.no.aletrainsystem.pointswitch.SwitchState finalState;
	public ntnu.no.aletrainsystem.pointswitch.SwitchState currentState;
	
	public boolean isAlreadyInFinalState(){
		return finalState == currentState;
	}
}
