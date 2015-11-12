package aletrainsystem.models.locking;

import java.util.Set;

import aletrainsystem.models.TrainId;

public class Request implements LockingMessage {
	
	private final TransactionId id;
	private final TrainId collector;
	private final Set<Lockable> objects;
	private RequestType type;
	
	public Request(TransactionId id, TrainId collector, Set<Lockable> objects, RequestType type) {
		this.id = id;
		this.collector = collector;
		this.objects = objects;
		this.type = type;
	}

	@Override
	public TransactionId transactionId() {
		return id;
	}
	
	@Override
	public TrainId collector() {
		return collector;
	}
	
	public Set<Lockable> objectsToLock() {
		return objects;
	}
	
	public void setType(RequestType type) {
		this.type = type;
	}
	
	@Override
	public RequestType type() {
		return type;
	}
}
