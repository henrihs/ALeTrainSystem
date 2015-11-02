package aletrainsystem.models.Navigation;

import java.util.LinkedList;
import java.util.Iterator;

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
		
	public RailComponent getFirst(){
		return parts.getFirst();
	}
	
	public void moveTo(RailComponent part) {
		parts.addLast(part);
		if (parts.size() > sizeOfParentObject) {
			parts.removeFirst();
		}
	}

	@Override
	public Iterator<RailComponent> iterator() {
		Iterator<RailComponent> ipart = parts.iterator();
		return ipart;
	}
}
