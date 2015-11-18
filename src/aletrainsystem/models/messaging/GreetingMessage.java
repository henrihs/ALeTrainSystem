package aletrainsystem.models.messaging;

import java.util.Set;

import aletrainsystem.models.TrainId;
import aletrainsystem.models.railroad.IRailroad;

public class GreetingMessage {
	private TrainId entrant;
	private Set<TrainId> trainsInSystem;
	private IRailroad latestMap;
	
	public GreetingMessage(TrainId entrant, Set<TrainId> trainsInSystem, IRailroad latestMap) {
		this.trainsInSystem = trainsInSystem;
		this.latestMap = latestMap;
	}
	
	public TrainId entrant() {
		return entrant;
	}
	
	public Set<TrainId> trainsInSystem() {
		return trainsInSystem;
	}
	
	public IRailroad latestMap() {
		return latestMap;
	}
}
