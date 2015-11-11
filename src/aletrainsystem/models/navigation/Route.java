package aletrainsystem.models.navigation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Route implements Iterable<RouteElement> {
	protected ArrayList<RouteElement> viaPoints;
	
	public Route() {
		viaPoints = new ArrayList<>();
	}
	
	public Route(Route copyFrom) {
		viaPoints = (ArrayList<RouteElement>) copyFrom.viaPoints.clone();
	}
	
	public void add(RouteElement viaPoint){
		viaPoints.add(viaPoint);
	}
	
	public int componentLength() {
		return viaPoints.size();
	}
	
	public int brickLength() {
		int i = 0;
		for (RouteElement destination : viaPoints) {
			i += destination.length();
		}
		
		return i;
	}
	
	public RouteElement getFirstElement() {
		return viaPoints.get(0);
	}
	
	public RouteElement getLastElement() {
		return viaPoints.get(viaPoints.size()-1);
	}

	@Override
	public Iterator<RouteElement> iterator() {
		Iterator<RouteElement> iviapoints = viaPoints.iterator();
		return iviapoints;
	}
	
	public RouteElement getNext(RouteElement current) {
		int currentIndex = viaPoints.indexOf(current);
		if (currentIndex < viaPoints.size()) {
			return viaPoints.get(currentIndex+1);
		}
		
		return null;
	}
}