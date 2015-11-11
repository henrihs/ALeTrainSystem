package aletrainsystem.models.navigation;

import java.util.LinkedList;
import java.util.Iterator;
import aletrainsystem.models.railroad.PointConnector;
import aletrainsystem.models.railroad.RailBrick;
import aletrainsystem.models.railroad.RailComponent;

public class Position implements Iterable<RailComponent> {

	private LinkedList<RailComponent> parts;
	private int sizeOfParentObject;
	
	public Position(RailComponent[] arg0, int sizeOfParentObject) {
		parts = new LinkedList<RailComponent>();
		this.sizeOfParentObject = sizeOfParentObject;
		for (RailComponent railPart : arg0) {
			parts.add(railPart);
		}
	}
		
	public RailComponent head(){
		return parts.getFirst();
	}
	
	public RailBrick getPreviousBrick(){
		for (RailComponent railComponent : parts) {
			if (railComponent != head() && railComponent instanceof RailBrick)
				return (RailBrick) railComponent;
		}
		return null;
	}
	
	public void moveTo(RailComponent part) {
		parts.addLast(part);
		if (parts.size() > sizeOfParentObject) {
			parts.removeFirst();
		}
	}
	
	public RailComponent moveInDirection(PointConnector direction) {
		RailComponent nextComponent = lookAhead(direction);
		moveTo(nextComponent);
		return nextComponent;
	}
	
	public RailComponent lookAhead(PointConnector direction) {
		if (head() instanceof RailBrick) {
			RailBrick frontBrick = (RailBrick)head();
			return frontBrick.parentLeg().getNextComponent(frontBrick, direction);
		}
		
		return head();
	}
	
	public boolean headIsInPointSwitch(){
		return head() instanceof PointConnector;
	}
	
	public void turnAround(){
		LinkedList<RailComponent> reversedParts = new LinkedList<>();
		for (RailComponent railComponent : parts) {
			reversedParts.addFirst(railComponent);
		}
		
		parts = reversedParts;
	}
	
	@Override
	public Iterator<RailComponent> iterator() {
		Iterator<RailComponent> ipart = parts.iterator();
		return ipart;
	}
}