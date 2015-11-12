package aletrainsystem.mapcontroller;

import aletrainsystem.enums.PointConnectorEnum;
import aletrainsystem.enums.SpeedLevel;
import aletrainsystem.models.TrainId;
import aletrainsystem.models.navigation.Position;
import aletrainsystem.models.navigation.Route;
import aletrainsystem.models.navigation.RouteElement;
import aletrainsystem.models.railroad.PointConnector;
import aletrainsystem.models.railroad.RailBrick;
import aletrainsystem.models.railroad.RailComponent;
import aletrainsystem.models.railroad.StartLeg;
import no.ntnu.item.arctis.runtime.Block;

public class MapController extends Block {

	public TrainId id;
	public aletrainsystem.models.railroad.IRailroad map;
	public aletrainsystem.models.navigation.Position position;
	public aletrainsystem.models.navigation.Route currentRoute;
	public aletrainsystem.models.railroad.PointConnector direction;
	public aletrainsystem.models.navigation.RouteElement currentRouteElement;

	public Position init(MapInitParams params) {
		map = params.railroad();
		id = params.id();
		StartLeg start = map.getRailSystemStartLeg();
		
		direction = start.getConnector();  
		position = new Position(new RailComponent[] {start.getConnector()}, params.sizeOfParentObject());
		return position;
	}

	public SpeedLevel slowCommand() {
		return SpeedLevel.LOW;
	}

	public SpeedLevel stopCommand() {
		return SpeedLevel.STOPPED;
	}

	public void initCurrentRouteElement(Route route) {
		currentRouteElement = route.getFirstElement();
	}

	public boolean isRouteLocked() {
		for (RouteElement routeElement : currentRoute) {
			if (routeElement instanceof PointConnector) {
				if (!id.equals(((PointConnector)routeElement).point().checkLock()))
					return false;
			}
		}
		
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
}
