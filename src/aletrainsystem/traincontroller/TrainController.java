package aletrainsystem.traincontroller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import aletrainsystem.mapcontroller.MapInitParams;
import aletrainsystem.models.TrainId;
import aletrainsystem.models.locking.Lockable;
import aletrainsystem.models.messaging.GreetingMessage;
import aletrainsystem.models.messaging.JoinMessage;
import aletrainsystem.models.navigation.Route;
import aletrainsystem.models.navigation.RouteElement;
import aletrainsystem.models.railroad.IRailroad;
import aletrainsystem.models.railroad.RailroadBuilder;
import no.ntnu.item.arctis.runtime.Block;

public class TrainController extends Block {
	
	public aletrainsystem.models.TrainId id;
	public aletrainsystem.models.railroad.IRailroad railroad;
	public java.util.Set<aletrainsystem.models.TrainId> trainsInProximity;
	public LinkedList<aletrainsystem.models.navigation.RouteElement> servedStations;
	private Iterator<RouteElement> stationIterator;
	public aletrainsystem.models.navigation.RouteElement servingStation;
	public void logAndThrow(String errorMessage) {
		logger.error(errorMessage);
	}
	
	
	public void setVariablesFromProperties() {
		id = new TrainId((String) getProperty(TrainId.ID_KEY));
		RailroadBuilder builder = new RailroadBuilder();
		railroad = builder.build((String) getProperty(IRailroad.PROPERTYNAME));
		initServedStationList();
	}

	private void initServedStationList() {
		servedStations = new LinkedList<RouteElement>();
		String[] stations = ((String) getProperty(IRailroad.STATIONLISTKEY)).split(",");
		for (String string : stations) {
			if (!railroad.isStation(string)) {
				logWarn("Could not find station with id".concat(string));
				continue;
			}
			servedStations.add(railroad.getRouteElement(string));
		}
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
		if (greeting != null) {
			railroad = greeting.latestMap();
			trainsInProximity = greeting.trainsInSystem();			
		}
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


	public JoinMessage generateJoinMessage() {
		return new JoinMessage(id);
	}


	public GreetingMessage generateGreeting(JoinMessage join) {
		Set<TrainId> allTrains = new HashSet<TrainId>(trainsInProximity);
		allTrains.add(id);
		return new GreetingMessage(join.id(), allTrains, railroad);
	}


	public RouteElement getNextServedStation() {
		if (stationIterator == null || !stationIterator.hasNext()) {
			stationIterator = servedStations.iterator();
		}
		
		return stationIterator.next();
	}
}
