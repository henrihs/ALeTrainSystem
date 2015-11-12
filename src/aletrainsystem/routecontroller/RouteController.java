package aletrainsystem.routecontroller;

import java.util.ArrayList;
import java.util.Iterator;

import aletrainsystem.algorithms.GreedyAlgorithm;
import aletrainsystem.algorithms.ShortestPathUniDirectional;
import aletrainsystem.models.navigation.Route;
import aletrainsystem.models.navigation.RouteElement;
import aletrainsystem.models.railroad.PointConnector;
import aletrainsystem.models.railroad.RegularLeg;
import no.ntnu.item.arctis.runtime.Block;

public class RouteController extends Block {

	private PointConnector direction;
	public aletrainsystem.models.navigation.RouteElement nextDestination;
	public aletrainsystem.models.navigation.Position currentPosition;
	public aletrainsystem.models.railroad.PointConnector currentDirection;
	public aletrainsystem.models.railroad.IRailroad railroad;
	public java.util.ArrayList<aletrainsystem.models.navigation.Route> subRoutes;
	public java.util.Iterator<aletrainsystem.models.navigation.Route> subRouteIterator;
	public Route findRoute(RouteElement destination) {
		ShortestPathUniDirectional algorithm = new GreedyAlgorithm();
		return algorithm.findSingleShortestPath(railroad, currentPosition, destination, direction);
	}
	
	public ArrayList<Route> splitIntoSubRoutes(Route route) {
		ArrayList<Route> subRoutes = new ArrayList<>();
		Route subRoute = new Route();
		
		for (RouteElement routeElement : route) {
			subRoute.add(routeElement);
			if (routeElement instanceof RegularLeg) {
				RegularLeg leg = (RegularLeg) routeElement;
				if (railroad.isStation(leg)) {
					subRoutes.add(subRoute);
					subRoute = new Route();
				}
			}
		}
		
		return subRoutes;
	}
	
	public Iterator<Route> getSubRouteIterator() {
		return subRoutes.iterator();
	}

	public Route getNextSubRoute() {
		if (subRouteIterator.hasNext()) {
			return subRouteIterator.next();
		}
		return null;
	}
}
