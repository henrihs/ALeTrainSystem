package aletrainsystem.algorithms;

import aletrainsystem.models.navigation.RouteDescriptor;

public class RouteNotFoundException extends Exception {

	public RouteNotFoundException(RouteDescriptor descriptor) {
		this("No route found from start '" + 
				descriptor.getStart().toString() + 
				"' to destination '" + 
				descriptor.getDestination().toString() + 
				"'.");
	}
	
	public RouteNotFoundException(String message) {
		super(message);
	}
}
