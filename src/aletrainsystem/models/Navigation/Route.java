package aletrainsystem.models.Navigation;

import java.util.LinkedList;

public class Route {
	protected LinkedList<Destination> viaPoints;
	
	public Route() {
		viaPoints = new LinkedList<>();
	}
	
	public Route(Route copyFrom) {
		viaPoints = (LinkedList<Destination>) copyFrom.viaPoints.clone();
	}
	
	public void add(Destination destination){
		viaPoints.addLast(destination);
	}
	
	public int componentLength() {
		return viaPoints.size();
	}
	
	public int brickLength() {
		int i = 0;
		for (Destination destination : viaPoints) {
			i += destination.length();
		}
		
		return i;
	}
}
