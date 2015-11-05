package aletrainsystem.algorithms;

import java.util.ArrayList;
import aletrainsystem.models.Navigation.RouteElement;
import aletrainsystem.models.Navigation.Position;
import aletrainsystem.models.Navigation.Route;
import aletrainsystem.models.railroad.PointConnector;
import aletrainsystem.models.railroad.RailBrick;
import aletrainsystem.models.railroad.RegularLeg;

public class GreedyAlgorithm implements ShortestPathUniDirectional {
	
	private RouteElement finalDestination;
	private ArrayList<Route> routes;
	
	@Override
	public Route findSingleShortestPath(Position position, RouteElement destination, PointConnector direction) {
		finalDestination = destination;
		
		routes = new ArrayList<>();
		
		RouteElement start;
		RouteElement previous;
		
		if (position.headIsInPointSwitch()) {
			start = (RouteElement) position.head();
			previous = position.getPreviousBrick().parentLeg();
		}
		else {
			RailBrick startBrick = (RailBrick)position.head();
			start = startBrick.parentLeg();
			previous = ((RegularLeg)start).getOppositeConnector(direction);
		}
		
		traverseAllPaths(new Route(), start, previous);
		
		Route shortestRoute = null;
		for (Route route : routes) {
			if (shortestRoute == null 
				|| route.brickLength() < shortestRoute.brickLength())
				shortestRoute = route;
		}
		
		return shortestRoute;		
	}
	
	private void traverseAllPaths(Route continuedRoute, RouteElement current, RouteElement previous) {
		continuedRoute.add(current);
		if (current.equals(finalDestination)) {
			return;
		}
		
		RouteElement[] next = current.getNext(previous);
		traverseAllPaths(continuedRoute, next[0], current);
		if (next.length == 2) {
			Route alternativeRoute = new Route(continuedRoute);
			routes.add(alternativeRoute);
			traverseAllPaths(alternativeRoute, next[1], current);
		}
	}
}
