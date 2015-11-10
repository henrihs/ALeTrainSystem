package aletrainsystem.models.navigation;

public abstract class RouteElement {
	
	
	
	public abstract RouteElement[] getNext(RouteElement previous);
	public abstract int length();
	
	
}
