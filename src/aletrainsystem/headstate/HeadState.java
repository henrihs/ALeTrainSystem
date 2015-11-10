package aletrainsystem.headstate;

import aletrainsystem.enums.SleeperColor;
import no.ntnu.item.arctis.runtime.Block;


public class HeadState extends Block {

	public SleeperColor passed;
	public aletrainsystem.enums.PointConnectorEnum enterPointSwitch;
	public aletrainsystem.enums.PointConnectorEnum leavePointSwitch;
	public boolean passedRegularSleeper() {
		return passed == SleeperColor.regularSleeper();
	}
	
	public void enteredOnType(){
		enterPointSwitch = passed.convertToConnector();
	}
	
	public void leavedOnType(){
		leavePointSwitch = passed.convertToConnector();
	}
}
