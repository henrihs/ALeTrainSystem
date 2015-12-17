package aletrainsystem.algorithms;

import aletrainsystem.models.navigation.Route;
import aletrainsystem.models.navigation.RouteDescriptor;
import aletrainsystem.models.railroad.IRailroad;
import aletrainsystem.pointswitch.PointConnector;

public interface ShortestPathUniDirectional {
	Route findSingleShortestPath(IRailroad railroad, RouteDescriptor routeDescriptor, PointConnector direction) throws RouteNotFoundException;
}
