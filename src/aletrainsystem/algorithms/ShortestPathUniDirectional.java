package aletrainsystem.algorithms;

import aletrainsystem.models.Navigation.RouteElement;
import aletrainsystem.models.Navigation.Position;
import aletrainsystem.models.Navigation.Route;
import aletrainsystem.models.railroad.PointConnector;
import aletrainsystem.models.railroad.Railroad;

public interface ShortestPathUniDirectional {
	Route findSingleShortestPath(Railroad railroad, Position position, RouteElement destination, PointConnector direction);
}
