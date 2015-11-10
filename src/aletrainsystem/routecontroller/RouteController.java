package aletrainsystem.routecontroller;

import java.util.ArrayList;

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

	public Route findRoute(RouteElement destination) {
		ShortestPathUniDirectional algorithm = new GreedyAlgorithm();
		return algorithm.findSingleShortestPath(currentPosition, destination, direction);
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
}
