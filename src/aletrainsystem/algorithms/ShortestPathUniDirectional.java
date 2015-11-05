package aletrainsystem.algorithms;

import aletrainsystem.models.Navigation.RouteElement;
import aletrainsystem.models.Navigation.Position;
import aletrainsystem.models.Navigation.Route;
import aletrainsystem.models.railroad.PointConnector;

public interface ShortestPathUniDirectional {
	Route findSingleShortestPath(Position position, RouteElement destination, PointConnector direction);
}
