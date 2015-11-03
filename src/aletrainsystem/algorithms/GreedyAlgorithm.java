package aletrainsystem.algorithms;

import java.util.ArrayList;
import aletrainsystem.models.Navigation.Destination;
import aletrainsystem.models.Navigation.Position;
import aletrainsystem.models.Navigation.Route;
import aletrainsystem.models.railroad.PointSwitch;
import aletrainsystem.models.railroad.RailBrick;
import aletrainsystem.models.railroad.RailLeg;
import aletrainsystem.models.railroad.Railroad;

public class GreedyAlgorithm implements ShortestPathUniDirectional {
	
	private Destination finalDestination;
	private ArrayList<Route> routes;
	
	@Override
	public Route findSingleShortestPath(Railroad railroad, Position position, Destination destination, PointSwitch direction) {
		finalDestination = destination;
		
		routes = new ArrayList<>();
		Route route = new Route();
		
		Destination start;
		Destination previous;
		
		if (position.headIsInPointSwitch()) {
			start = position.findPointSwitchConnector();
			previous = position.getPreviousBrick().parentLeg();
		}
		else {
			RailBrick startBrick = (RailBrick)position.head();
			start = startBrick.parentLeg();
			previous = ((RailLeg)start).getOppositeConnector(direction);
		}
		
		traverseAllPaths(route, start, previous);
		
	}
	
	private void traverseAllPaths(Route route, Destination current, Destination previous) {
		route.add(current);
		if (current.equals(finalDestination)) {
			return;
		}
		
		Destination[] next = current.getNext(previous);
		traverseAllPaths(route, next[0], current);
		if (next.length == 2) {
			Route alternativeRoute = new Route(route);
			routes.add(alternativeRoute);
			traverseAllPaths(alternativeRoute, next[1], current);
		}
	}

	private void startInPointSwitch(){
		
	}
	
	private void startOnRailLeg() {
		
	}

}
