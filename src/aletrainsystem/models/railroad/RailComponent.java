package aletrainsystem.models.railroad;

import aletrainsystem.models.RailComponentId;
import aletrainsystem.models.navigation.RouteElement;

public interface RailComponent {
	public RailComponentId id();
	public RouteElement partOfElement();
}
