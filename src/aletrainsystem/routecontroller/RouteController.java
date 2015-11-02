package aletrainsystem.routecontroller;

import aletrainsystem.enums.SleeperColor;
import aletrainsystem.models.RailPartId;
import aletrainsystem.models.Navigation.Destination;
import aletrainsystem.models.Navigation.Position;
import aletrainsystem.models.Navigation.Route;
import aletrainsystem.models.railroad.Railroad;
import no.ntnu.item.arctis.runtime.Block;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class RouteController extends Block {

	public Railroad railroad;
	public Destination nextDestination;
	private Position currentPosition;
	private RailPartId direction;
	private int sleeperMod;

	public void initialize(Railroad r) {
	}

	public Route findRoute(Destination d) {
		nextDestination = d;
		return findShortestRoute(d);
	}
	
	public void updatePosition(SleeperColor s) {
		if (s != SleeperColor.regularSleeper()) {
			// Do stuff, new signal color detected
		}
		else {
			sleeperMod++;
			
		}
	}
	
	private Route findShortestRoute(Destination d) {
		throw new NotImplementedException();
	}

}
