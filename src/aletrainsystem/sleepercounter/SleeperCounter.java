package aletrainsystem.sleepercounter;

import no.ntnu.item.arctis.runtime.Block;

public class SleeperCounter extends Block {

	public aletrainsystem.models.railroad.RailBrick brick;
	public int counter = 0;
	public aletrainsystem.models.railroad.RailBrick passedBrick;

	public void incrementCounter() {
		counter++;
	}
	
	public boolean isLastSleeperOnBrick(){
		return counter >= brick.sleepers();
	}
		
}
