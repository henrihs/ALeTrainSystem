package aletrainsystem.models.locking;

import java.util.Set;

import aletrainsystem.models.TrainId;
import aletrainsystem.models.railroad.IRailroad;

public class GreetingMessage {
	private Set<TrainId> trainsInSystem;
	private IRailroad latestMap;
	
	public GreetingMessage(Set<TrainId> trainsInSystem, IRailroad latestMap) {
		this.trainsInSystem = trainsInSystem;
		this.latestMap = latestMap;
	}
	
	public Set<TrainId> trainsInSystem() {
		return trainsInSystem;
	}
	
	public IRailroad latestMap() {
		return latestMap;
	}
}
