package aletrainsystem.models.messaging;

import aletrainsystem.models.TrainId;

public class JoinMessage {
	private TrainId id;
	
	public JoinMessage(TrainId id) {
		this.id = id;
	}
	
	public TrainId id() {
		return id;
	}
}
