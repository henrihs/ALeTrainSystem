package aletrainsystem.mapcontroller;

import aletrainsystem.enums.SpeedLevel;
import aletrainsystem.models.navigation.Position;
import aletrainsystem.models.railroad.PointConnector;
import aletrainsystem.models.railroad.RailComponent;
import aletrainsystem.models.railroad.StartLeg;
import no.ntnu.item.arctis.runtime.Block;

public class MapController extends Block {

	public aletrainsystem.models.railroad.IRailroad map;
	public aletrainsystem.models.navigation.Position position;
	public PointConnector direction;

	public Position init(MapInitParams params) {
		map = params.railroad();
		
		StartLeg start = map.getRailSystemStartLeg();
		
		direction = start.getConnector();  
		position = new Position(new RailComponent[] {start.getConnector()}, params.sizeOfParentObject());
		return position;
	}

	public void incrementPosition() {
		position.moveInDirection(direction);
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

}
