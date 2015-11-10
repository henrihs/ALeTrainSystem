package aletrainsystem.models.locking;

import aletrainsystem.models.TrainId;

public class Response {
	
	private Request request;
	private TrainId responder;
	private boolean success;
	
	public Response(TrainId participant, Request request, boolean success) {
		this.request = request;
		this.success = success;
		this.responder = participant;
	}
	
	public boolean success() {
		return success;
	}
	
	public TrainId requester() {
		return request.coordinator();
	}
	
	public TransactionId transactionId() {
		return request.id();
	}
	
	public TrainId responder() {
		return responder;
	}
}
