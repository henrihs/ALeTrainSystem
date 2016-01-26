package aletrainsystem.algorithms;

import java.util.ArrayList;

import aletrainsystem.models.RailComponentId;
import aletrainsystem.models.navigation.Position;
import aletrainsystem.models.navigation.Route;
import aletrainsystem.models.navigation.RouteDescriptor;
import aletrainsystem.models.navigation.RouteElement;
import aletrainsystem.models.railroad.IRailroad;
import aletrainsystem.models.railroad.RailBrick;
import aletrainsystem.models.railroad.RailLeg;
import aletrainsystem.models.railroad.RegularLeg;
import aletrainsystem.models.railroad.StartLeg;
import aletrainsystem.pointswitch.PointConnector;

public class GreedyAlgorithm implements ShortestPathUniDirectional {

	private RouteElement finalDestination;
	private ArrayList<Route> routes;
	private RouteElement startPosition;
	private IRailroad railroad;

	@Override
<<<<<<< HEAD
	public Route findSingleShortestPath(IRailroad railroad, RouteDescriptor routeDescriptor, PointConnector direction) throws RouteNotFoundException {
		finalDestination = routeDescriptor.getDestination();
		RailLeg start = routeDescriptor.getStart();
=======
	public Route findSingleShortestPath(IRailroad railroad, RouteDescriptor routeDescritor, PointConnector direction) throws RouteNotFoundException {
		finalDestination = routeDescritor.getDestination();
		RailLeg start = routeDescritor.getStart();
>>>>>>> ec96013b6c0f884922639ca66aaa40160f9e2d6d
		this.railroad = railroad;
		routes = new ArrayList<>();
		RouteElement previous = null;
		RouteElement startPoint = null;

		if (start instanceof StartLeg) {
			previous = start;
			startPoint = ((StartLeg) previous).getConnector();
		} else {
			previous = start.getOppositeConnector(direction);
			startPoint = start;
		}

		traverseAllPaths(new Route(), startPoint, previous);

		Route shortestRoute = null;
		for (Route route : routes) {
			if (shortestRoute == null || route.brickLength() < shortestRoute.brickLength())
				shortestRoute = route;
		}
		
		if (shortestRoute == null) {
<<<<<<< HEAD
			throw new RouteNotFoundException(routeDescriptor);
=======
			throw new RouteNotFoundException(routeDescritor);
>>>>>>> ec96013b6c0f884922639ca66aaa40160f9e2d6d
		}

		return shortestRoute;
	}

	private void traverseAllPaths(Route continuedRoute, RouteElement current, RouteElement previous) {
		if (continuedRoute.contains(current) || (continuedRoute.brickLength() > 1 && current.equals(startPosition))
				|| current instanceof StartLeg) {
			return;
		}

		continuedRoute.add(current);
		if (current.equals(finalDestination)) {
			routes.add(continuedRoute);
			return;
		}

		RouteElement[] next = current.getNext(previous);
		if (next == null || next.length < 1) {
			return;
		}

		if (next.length == 1) {
			traverseAllPaths(continuedRoute, next[0], current);
		}

		else if (isStation(next)) {
			RouteElement choice = chooseDirection(next);
			traverseAllPaths(continuedRoute, choice, current);
		}

		else if (next.length == 2) {
			Route alternativeRoute = new Route(continuedRoute);
			traverseAllPaths(continuedRoute, next[0], current);
			traverseAllPaths(alternativeRoute, next[1], current);
		}
	}

	private boolean isStation(RouteElement[] next) {
		if (next.length == 2) {
			RailLeg possibleStation = ((PointConnector) next[0]).getConnectedRailLeg();
			if (possibleStation instanceof RegularLeg && railroad.isStation((RegularLeg) possibleStation)) {
				return true;
			}
		}

		return false;
	}

	private RouteElement chooseDirection(RouteElement[] choices) {
		PointConnector throughConnector = (PointConnector) choices[0];
		RailComponentId pointId = throughConnector.id();
		RailComponentId oppositePointId = ((RegularLeg) throughConnector.getConnectedRailLeg())
				.getOppositeConnector(throughConnector).id();
		if (pointId.compareTo(oppositePointId) < 0) {
			return choices[0];
		}

		return choices[1];
	}
}
