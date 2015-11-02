package aletrainsystem.models.Navigation;

import java.util.Stack;

public class Route {
	private Stack<Destination> viaPoints;
	
	public Route(Destination[] viaPoints) {
		this.viaPoints = new Stack<Destination>();
		for (Destination destination : viaPoints) {
			this.viaPoints.push(destination);
		}
	}
}
