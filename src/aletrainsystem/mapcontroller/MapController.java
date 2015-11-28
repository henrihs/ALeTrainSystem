package aletrainsystem.mapcontroller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import aletrainsystem.enums.PointConnectorEnum;
import aletrainsystem.enums.SpeedLevel;
import aletrainsystem.models.TrainId;
import aletrainsystem.models.locking.Lockable;
import aletrainsystem.models.messaging.PointSwitchOrder;
import aletrainsystem.models.navigation.Position;
import aletrainsystem.models.navigation.Route;
import aletrainsystem.models.navigation.RouteElement;
import aletrainsystem.models.railroad.RailBrick;
//import aletrainsystem.models.railroad.RailComponent;
import aletrainsystem.models.railroad.StartLeg;
import aletrainsystem.pointswitch.PointConnector;
import no.ntnu.item.arctis.runtime.Block;

public class MapController extends Block {

	public TrainId id;
	public aletrainsystem.models.navigation.Position position;
	public aletrainsystem.models.navigation.Route currentRoute;
	public aletrainsystem.models.navigation.RouteElement currentRouteElement;
	public aletrainsystem.models.railroad.Railroad map;
	public aletrainsystem.pointswitch.PointConnector direction;

	public Position init(MapInitParams params) {
		map = params.railroad();
		id = params.id();
		StartLeg start = map.getRailSystemStartLeg();
		
		direction = start.getConnector().point().getConnector(PointConnectorEnum.ENTRY);  
//		position = new Position(new RailComponent[] {start.getConnector()}, params.sizeOfParentObject());
		position = new Position(start.getStartOfLeg(params.sizeOfParentObject()), params.sizeOfParentObject());
		
		logger.info("Initializing");
		return position;
	}
	
	public boolean isOnLastElement() {
		return currentRouteElement == currentRoute.getLastElement();
	}

	public SpeedLevel slowCommand() {
		return SpeedLevel.LOW;
	}

	public SpeedLevel stopCommand() {
		String timestamp = String.valueOf(System.currentTimeMillis());
		logger.info("Breaking at ".concat(timestamp));
		return SpeedLevel.STOPPED;
	}

	public void initCurrentRouteElement(Route route) {
		logger.info("Waiting for lock on route ".concat(route.toString()));
		direction = (PointConnector)route.getSecondElement();
		currentRouteElement = route.getFirstElement();
	}

	public boolean isRouteLocked(Set<Lockable> lockedObjects) {
		for (Lockable lockable : lockedObjects) {
			if (!id.equals(lockable.checkLock())) {
				return false;
			}
		}
		
		logger.info("Lock acquired for route ".concat(currentRoute.toString()));
		return true;
	}

	public SpeedLevel driveCommand() {
		return SpeedLevel.MEDIUM;
	}

	public void logLockingErr() {
		logger.error("Expected lock on " + currentRoute + " not successfull!");
	}

	public MapController getThisInstance() {
		return this;
	}

	public void logInit() {
		logger.info("Initialized");
	}

	public ArrayList<RouteElement> removeFromCurrentRoute(ArrayList<RouteElement> elements) {
		if (currentRoute == null)
			return null;
		ArrayList<RouteElement> readyToUnlock = new ArrayList<>();
		elements.forEach(e -> {
			currentRoute.remove(e);
			if (!position.isTouchingLockable(e.getLockableResource()))
				readyToUnlock.add(e);
			});
		
		currentRouteElement = currentRoute.getFirstElement();
		return readyToUnlock.size() > 0 ? readyToUnlock : null;
	}

	public RailBrick nextbrick(RailBrick r) {
		return r;
	}

	public Set<PointSwitchOrder> generateOrdersFromRoute() {
		Set<PointSwitchOrder> orders = new HashSet<>();
		RouteElement previous = null;
		for (RouteElement current : currentRoute) {
			if (previous instanceof PointConnector
				&& current instanceof PointConnector) {
				PointConnector connector = (PointConnector) current;
				if (connector.getType() == PointConnectorEnum.DIVERT 
						|| connector.getType() == PointConnectorEnum.THROUGH) {
					orders.add(new PointSwitchOrder(connector.id(), connector.getType()));
				}
			}
			previous = current;
		}
		logger.info("Generated orders: " + orders.toString());
		return orders;
	}
}
