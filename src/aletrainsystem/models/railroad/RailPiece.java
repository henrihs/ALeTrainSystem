package aletrainsystem.models.railroad;

import aletrainsystem.models.RailPartId;
import bluebrick4j.model.BrickType;

public class RailPiece implements RailPart {
	
	private RailPartId id;
	private final RailLeg parentLeg;
	private final int sleepers;
	
	public RailPiece(String id, RailLeg parent, BrickType brickType) {
		this.id = new RailPartId(id);
		parentLeg = parent;
		if (brickType == BrickType.CURVED || brickType == BrickType.STRAIGHT) {
			this.sleepers = 4;
		}
		else {
			this.sleepers = 10000;
		}
	}
	
	public int sleepers(){
		return sleepers;
	}

	@Override
	public RailPartId id() {
		return id;
	}
}
