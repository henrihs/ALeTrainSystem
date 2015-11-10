package aletrainsystem.models.locking;

import java.util.Set;

import aletrainsystem.models.TrainId;

public class Request {
	
	private final TransactionId id;
	private final TrainId coordinator;
	private final Set<Lockable> objects;
	private RequestType type;
	
	public Request(TransactionId id, TrainId coordinator, Set<Lockable> objects, RequestType type) {
		this.id = id;
		this.coordinator = coordinator;
		this.objects = objects;
		this.type = type;
	}

	public TransactionId id() {
		return id;
	}
	
	public TrainId coordinator() {
		return coordinator;
	}
	
	public Set<Lockable> objectsToLock() {
		return objects;
	}
	
	public void type(RequestType type) {
		this.type = type;
	}
	
	public RequestType type() {
		return type;
	}
}
