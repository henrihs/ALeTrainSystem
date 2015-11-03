package aletrainsystem.algorithms;

import aletrainsystem.models.Navigation.Destination;
import aletrainsystem.models.Navigation.Position;
import aletrainsystem.models.Navigation.Route;
import aletrainsystem.models.railroad.PointSwitch;
import aletrainsystem.models.railroad.Railroad;

public interface ShortestPathUniDirectional {
	Route findSingleShortestPath(Railroad railroad, Position position, Destination destination, PointSwitch direction);
}
