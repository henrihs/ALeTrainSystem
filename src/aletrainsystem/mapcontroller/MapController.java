package aletrainsystem.mapcontroller;

import aletrainsystem.enums.PointConnectorEnum;
import aletrainsystem.enums.SpeedLevel;
import aletrainsystem.models.navigation.Position;
import aletrainsystem.models.navigation.Route;
import aletrainsystem.models.navigation.RouteElement;
import aletrainsystem.models.railroad.PointConnector;
import aletrainsystem.models.railroad.RailBrick;
import aletrainsystem.models.railroad.RailComponent;
import aletrainsystem.models.railroad.StartLeg;
import no.ntnu.item.arctis.runtime.Block;

public class MapController extends Block {

	public aletrainsystem.models.railroad.IRailroad map;
	public aletrainsystem.models.navigation.Position position;
	public aletrainsystem.models.navigation.Route currentRoute;
	public aletrainsystem.models.railroad.PointConnector direction;
	public aletrainsystem.models.navigation.RouteElement currentRouteElement;

	public Position init(MapInitParams params) {
		map = params.railroad();
		
		StartLeg start = map.getRailSystemStartLeg();
		
		direction = start.getConnector();  
		position = new Position(new RailComponent[] {start.getConnector()}, params.sizeOfParentObject());
		return position;
	}

	public RailComponent incrementPosition() {
		return position.moveInDirection(direction);
	}

	public RailComponent getHead(Position p) {
		return p.head();
	}

	public SpeedLevel slowCommand() {
		return SpeedLevel.LOW;
	}

	public SpeedLevel stopCommand() {
		return SpeedLevel.STOPPED;
	}

	public boolean lastElementOnSubRoute(RailComponent component) {
		RouteElement element = null;
		
		if (component instanceof PointConnector) {
			PointConnector connector = (PointConnector) component;
			element = connector;
		}
		else if (component instanceof RailBrick) {
			RailBrick brick = (RailBrick) component;
			element = brick.parentLeg();
		}
		
		return element.equals(currentRoute.getLastElement());
	}

	public RouteElement getCurrentRouteElementFromPosition() {
		RailBrick brick = null;
		if (!position.headIsInPointSwitch()) {
			 brick = (RailBrick)position.head();
		}
		else {
			brick = (RailBrick)position.getPreviousBrick();
		}
		
		return brick.parentLeg();
	}

	public RailComponent jumpToNext(PointConnectorEnum connectorType) {
		while (!position.headIsInPointSwitch()) {
			position.moveInDirection(direction);
		}
		
		logIfUnexpectedConnector(connectorType);
		currentRouteElement = currentRoute.getNext(currentRouteElement);
		
		return position.head();
	}

	private void logIfUnexpectedConnector(PointConnectorEnum connectorType) {
		PointConnector pointInFront = (PointConnector) position.head();
		if (pointInFront.getType() != connectorType) {
			logger.warn(
					"Unexpected connector type! Expected '".
					concat(pointInFront.getType().toString()).
					concat("', actual '").
					concat(connectorType.toString()));
		}
	}

	public RailBrick castToRailBrick(RailComponent component) {
		if (component instanceof RailBrick)
			return (RailBrick) position.lookAhead(direction);
		return null;
	}

	public void setNewDirection(PointConnectorEnum connectorType) {
		logIfUnexpectedConnector(connectorType);
		
		
	}

	public void initCurrentRouteElement(Route route) {
		currentRouteElement = route.getFirstElement();
	}

}
