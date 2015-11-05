package aletrainsystem.mapcontroller;

import aletrainsystem.models.Pair;
import aletrainsystem.models.railroad.IRailroad;

public class MapInitParams extends Pair<IRailroad, Integer>{

	public MapInitParams(IRailroad railroad, Integer sizeOfParentObject) {
		super(railroad, sizeOfParentObject);
	}
	
	public IRailroad railroad() {
		return first();
	}
	
	public int sizeOfParentObject(){
		return second();
	}
	
}
