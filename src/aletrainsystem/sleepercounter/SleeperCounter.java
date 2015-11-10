package aletrainsystem.sleepercounter;

import no.ntnu.item.arctis.runtime.Block;
import aletrainsystem.enums.PointConnectorEnum;

public class SleeperCounter extends Block {

	public aletrainsystem.models.railroad.RailBrick brick;
	public int counter = 0;

	public void incrementCounter() {
		counter++;
	}
	
	public boolean isLastSleeperOnBrick(){
		return counter >= brick.sleepers();
	}
	
}
