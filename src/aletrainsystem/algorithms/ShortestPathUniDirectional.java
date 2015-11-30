package aletrainsystem.algorithms;

import aletrainsystem.models.navigation.Route;
import aletrainsystem.models.navigation.RouteElement;
import aletrainsystem.models.railroad.IRailroad;
import aletrainsystem.models.railroad.RailLeg;
import aletrainsystem.pointswitch.PointConnector;

public interface ShortestPathUniDirectional {
	Route findSingleShortestPath(IRailroad railroad, RailLeg start, RouteElement destination, PointConnector direction);
}
