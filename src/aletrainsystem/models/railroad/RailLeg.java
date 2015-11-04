package aletrainsystem.models.railroad;

import java.util.ArrayList;

import aletrainsystem.models.Navigation.RouteElement;

public abstract class RailLeg extends RouteElement {
	
	protected ArrayList<RailBrick> railBricks;
	
	protected RailLeg() {
		railBricks = new ArrayList<>();
	}
	
	public int length(){
		return railBricks.size();
	}
	
	public int getSleepersCount(){
		int sleepers = 0;
		for (RailBrick railBrick : railBricks) {
			sleepers += railBrick.sleepers();
		}
		
		return sleepers;
	}
	
	public void addRailBrick(RailBrick railBrick) {
		railBricks.add(railBrick);
	}
	
	public abstract RailComponent getNextComponent(RailComponent previous, PointSwitchConnector direction);
}
