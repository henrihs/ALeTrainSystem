package aletrainsystem.algorithms;

import aletrainsystem.models.navigation.Position;
import aletrainsystem.models.navigation.Route;
import aletrainsystem.models.navigation.RouteElement;
import aletrainsystem.models.railroad.PointConnector;

public interface ShortestPathUniDirectional {
	Route findSingleShortestPath(Position position, RouteElement destination, PointConnector direction);
}
