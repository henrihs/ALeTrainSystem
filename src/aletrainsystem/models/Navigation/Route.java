package aletrainsystem.models.Navigation;

import java.util.LinkedList;
import java.util.Stack;

public class Route {
	private LinkedList<Destination> viaPoints;
	
	public Route(LinkedList<Destination> viaPoints) {
		this.viaPoints = viaPoints;
	}
}
