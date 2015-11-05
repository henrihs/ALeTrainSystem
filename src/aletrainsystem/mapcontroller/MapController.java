package aletrainsystem.mapcontroller;

import aletrainsystem.models.Navigation.Position;
import aletrainsystem.models.railroad.RailComponent;
import aletrainsystem.models.railroad.StartLeg;
import no.ntnu.item.arctis.runtime.Block;

public class MapController extends Block {

	public aletrainsystem.models.railroad.IRailroad map;
	public aletrainsystem.models.Navigation.Position position;

	public void init(MapInitParams params) {
		map = params.railroad();
		
		StartLeg start = map.getRailSystemStartLeg();
		
		position = new Position(new RailComponent[] {}, params.sizeOfParentObject()); // ADD ENTRYPOINT HERE
	}

}
