package aletrainsystem.routecontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import aletrainsystem.algorithms.GreedyAlgorithm;
import aletrainsystem.algorithms.RouteNotFoundException;
import aletrainsystem.algorithms.ShortestPathUniDirectional;
import aletrainsystem.models.navigation.Route;
import aletrainsystem.models.navigation.RouteDescriptor;
import aletrainsystem.models.navigation.RouteElement;
import aletrainsystem.models.railroad.RailLeg;
import aletrainsystem.models.railroad.RegularLeg;
import aletrainsystem.pointswitch.Point;
import aletrainsystem.pointswitch.PointConnector;
import no.ntnu.item.arctis.runtime.Block;

public class RouteController extends Block {

	public aletrainsystem.models.navigation.RouteElement nextDestination;
	public aletrainsystem.models.navigation.Position currentPosition;
	public aletrainsystem.pointswitch.PointConnector currentDirection;
	public java.util.ArrayList<aletrainsystem.models.navigation.Route> subRoutes;
	public java.util.Iterator<aletrainsystem.models.navigation.Route> subRouteIterator;
	public aletrainsystem.models.railroad.Railroad railroad;
	public java.util.Map<RouteDescriptor, Route> routeCache = new HashMap<>();
	
	
	public void findRoute(RouteElement destination) {
		
		Runnable r = new Runnable() {
			@Override
			public void run() {
				ShortestPathUniDirectional algorithm = new GreedyAlgorithm();
				Route shortestRoute = null;
				RailLeg start = null;
				if (currentPosition.head() instanceof PointConnector) {
					start = currentPosition.getPreviousBrick().parentLeg();
				}
				else {
					start = (RailLeg) currentPosition.head().partOfElement();
				}
				
				RouteDescriptor routeInfo = new RouteDescriptor(start, destination);
				for (RouteDescriptor cachedRoute : routeCache.keySet()) {
					if (routeInfo.equals(cachedRoute)) {
						shortestRoute = new Route(routeCache.get(cachedRoute));
						logger.info("Using cached shortest route: ".concat(shortestRoute.toString()));
					}
				}
				
				if (shortestRoute == null) {
					try {
						shortestRoute = algorithm.findSingleShortestPath(railroad, routeInfo, currentDirection);
						routeCache.put(routeInfo, new Route(shortestRoute));
					} catch (RouteNotFoundException e) {
						logger.info("No route found!");
						e.printStackTrace();
					}
					logger.info("Shortest route calculated: ".concat(shortestRoute.toString()));
				}
				
				sendToBlock("ROUTECALCULATED", shortestRoute);
			}
		};
		
		runAsync(r);		
	}
	
	public ArrayList<Route> splitIntoSubRoutes(Route route) {
		ArrayList<Route> subRoutes = new ArrayList<>();
		Route subRoute = new Route();
		
		for (RouteElement routeElement : route) {
			subRoute.add(routeElement);
			if (routeElement instanceof RegularLeg) {
				RegularLeg leg = (RegularLeg) routeElement;
				if (leg != route.getFirstElement() && railroad.isStation(leg)) {
					subRoutes.add(subRoute);
					subRoute = new Route();
				}
			}
		}

		logger.info("Splitting route into ".concat(String.valueOf(subRoutes.size()).concat(" subroutes")));
		return subRoutes;
	}
	
	public Iterator<Route> getSubRouteIterator() {
		return subRoutes.iterator();
	}

	public Route getNextSubRoute() {
		if (subRouteIterator.hasNext()) {
			return subRouteIterator.next();
		}
		logger.info("Route finished");
		return null;
	}

	public void logInit() {
		logger.info("Initialized");
	}
}
