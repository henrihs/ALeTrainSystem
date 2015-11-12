package aletrainsystem.mapcontroller;

import aletrainsystem.models.Pair;
import aletrainsystem.models.TrainId;
import aletrainsystem.models.railroad.IRailroad;

public class MapInitParams {

	private IRailroad railroad;
	private Integer size;
	private TrainId id;

	public MapInitParams(IRailroad railroad, Integer sizeOfParentObject, TrainId id) {
		this.railroad = railroad;
		this.size = sizeOfParentObject;
		this.id = id;
	}
	
	public IRailroad railroad() {
		return railroad;
	}
	
	public int sizeOfParentObject(){
		return size;
	}
	
	public TrainId id() {
		return id;
	}
	
}
