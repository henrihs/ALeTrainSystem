package aletrainsystem.positionupdater;

import java.util.ArrayList;

import aletrainsystem.enums.PointConnectorEnum;
import aletrainsystem.mapcontroller.MapController;
import aletrainsystem.models.navigation.RouteElement;
import aletrainsystem.models.railroad.PointConnector;
import aletrainsystem.models.railroad.RailBrick;
import aletrainsystem.models.railroad.RailComponent;
import aletrainsystem.models.railroad.RailLeg;
import no.ntnu.item.arctis.runtime.Block;

public class PositionUpdater extends Block {

	public MapController parent;
	public aletrainsystem.models.railroad.RailComponent passedComponent;
	public java.util.ArrayList<aletrainsystem.models.navigation.RouteElement> passedElements;
	
	public RailLeg getCurrentLeg() {
		RailBrick brick = null;
		if (!parent.position.headIsInPointSwitch()) {
			 brick = (RailBrick)parent.position.head();
		}
		else {
			brick = (RailBrick)parent.position.getPreviousBrick();
		}
		
		return brick.parentLeg();
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
		
		return element.equals(parent.currentRoute.getLastElement());
	}
	
	public RailComponent jumpToNext(PointConnectorEnum connectorType) {
		RailComponent lastPopped = null;
		
		while (!parent.position.headIsInPointSwitch()) {
			lastPopped = parent.position.moveInDirection(parent.direction);
		}
				
		return lastPopped;
	}
	
	public RailBrick castToRailBrick(RailComponent component) {
		if (component instanceof RailBrick)
			return (RailBrick) parent.position.lookAhead(parent.direction);
		return null;
	}
	
	public RailComponent movePosition() {
		return parent.position.moveInDirection(parent.direction);
	}
	
	public RailComponent getHead() {
		return parent.position.head();
	}
	
	public RouteElement hasPassedElement(RailComponent component) {
		RouteElement elementInScope = component.partOfElement();
		if (parent.position.isTouchingRouteElement(elementInScope))
			return elementInScope;
		return null;
	}

	public void addToPassedElements(RouteElement element) {
		if (element != null)
			passedElements.add(element);
	}

	public void clearVariables() {
		passedComponent = null;
		if (passedElements == null)
			passedElements = new ArrayList<>();
		passedElements.clear();
	}
	
	public boolean hasPassedElements(){
		return passedElements.size() > 0;
	}
	
	public void logIfUnexpectedConnector(PointConnectorEnum connectorType) {
		PointConnector pointInFront = (PointConnector) parent.position.head();
		if (pointInFront.getType() != connectorType) {
			logger.warn(
					"Unexpected connector type! Expected '".
					concat(pointInFront.getType().toString()).
					concat("', actual '").
					concat(connectorType.toString()));
		}
	}
}
