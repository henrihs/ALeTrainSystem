package aletrainsystem.traincontroller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import aletrainsystem.mapcontroller.MapInitParams;
import aletrainsystem.models.TrainId;
import aletrainsystem.models.locking.GreetingMessage;
import aletrainsystem.models.locking.Lockable;
import aletrainsystem.models.navigation.Route;
import aletrainsystem.models.navigation.RouteElement;
import aletrainsystem.models.railroad.PointConnector;
import aletrainsystem.models.railroad.RailLeg;
import aletrainsystem.models.railroad.Railroad;
import aletrainsystem.models.railroad.RailroadBuilder;
import no.ntnu.item.arctis.runtime.Block;

public class TrainController extends Block {
	
	public aletrainsystem.models.TrainId id;
	public aletrainsystem.models.railroad.IRailroad railroad;
	public java.util.Set<aletrainsystem.models.TrainId> trainsInProximity;
	public void logAndThrow(String errorMessage) {
		logger.error(errorMessage);
	}
	
	
	public void setInitParamsFromProperties() {
		id = new TrainId((String) getProperty(TrainId.ID_KEY));
		RailroadBuilder builder = new RailroadBuilder();
		railroad = builder.build((String) getProperty(Railroad.PROPERTYNAME));
	}


	public MapInitParams getMapInitParams() {
		int sizeOfVessel = Integer.valueOf((String) getProperty(TrainId.VESSEL_SIZE_KEY));
		return new MapInitParams(railroad, sizeOfVessel, id);
	}

	public void addToProximitySet(TrainId train) {
		trainsInProximity.add(train);
	}


	public void removeFromProximitySet(TrainId train) {
		trainsInProximity.remove(train);
	}


	public void updateFieldsFromGreeting(GreetingMessage greeting) {
		railroad = greeting.latestMap();
		trainsInProximity = greeting.trainsInSystem();
	}


	public Set<Lockable> extractLockableSetFromRoute(Route route) {
		return extraxtLockableSet(route);
	}
	
	public Set<Lockable> extractLockableSetFromRouteElements(ArrayList<RouteElement> routeElements) {
		return extraxtLockableSet(routeElements);
	}
	
	private Set<Lockable> extraxtLockableSet(Iterable<RouteElement> iterable) {
		HashSet<Lockable> set = new HashSet<>();
		iterable.forEach(r -> set.add(r.getLockableResource()));
		
		return set;
	}
}
