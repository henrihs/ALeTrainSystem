package aletrainsystem.lockcoordinator;

import java.util.ArrayList;
import java.util.Set;

import aletrainsystem.models.TrainId;
import aletrainsystem.models.locking.CoordinatorInitParams;
import aletrainsystem.models.locking.LockingMessage;
import aletrainsystem.models.locking.Request;
import aletrainsystem.models.locking.RequestType;
import aletrainsystem.models.locking.Response;
import no.ntnu.item.arctis.runtime.Block;

public class LockCoordinator extends Block {
	
	public ArrayList<Response> responses = new ArrayList<>();
	public aletrainsystem.models.locking.TransactionId id;
	public java.util.Set<aletrainsystem.models.locking.Lockable> objectsToLock;
	public aletrainsystem.models.locking.Request request;
	private TrainId coordinatorId;
	public java.util.Set<aletrainsystem.models.TrainId> participants;
	public static String getAlias(CoordinatorInitParams params) {
		return params.getTransactionId().toString();
	}

	public static String getAlias(Response r) {
		return r.transactionId().toString();
	}

	public void saveInitParams(CoordinatorInitParams params) {
		this.coordinatorId = params.getCoordinatorId();
		this.id = params.getTransactionId();
		this.objectsToLock = params.getObjectsToLock();
		this.participants = params.getParticipants();
		logger.info("Starting locking procedure ".concat(id.toString()));
	}

	public Request generateReservationRequest() {
		Request request = new Request(id, coordinatorId, objectsToLock, RequestType.RESERVE);
		return request;
	}

	public boolean reservationAcquired(Response response) {
		return response.success();
	}

	public void saveResponse(Response r) {
		if (responses.stream().noneMatch(
				savedResponse -> savedResponse.respondent().equals(r.respondent())
				)) {
			logger.info(
					r.transactionId().toString().
					concat(": Received response from ").
					concat(r.respondent().toString().
					concat(" - ").
					concat(String.valueOf(r.success()))));
			responses.add(r);
		}
	}

	public boolean waitingForResponses() {
		if (responses.size() < participants.size())
			return true;
		return false;
	}

	public Request changeToPerformRequest(Request r) {
		logger.info(r.transactionId().toString().concat(": Performing lock"));
		r.setType(RequestType.PERFORM);
		return r;
	}

	public boolean allResponsesSuccessfull() {
		for (Response response : responses) {
			if (!response.success())
				return false;
		}
		
		return true;
	}

	public Request changeToAbortRequest(Request r) {
		logger.info(r.transactionId().toString().concat(": Aborting lock"));
		r.setType(RequestType.ABORT);
		return r;
	}

	public void log() {
		String failedAt = responses.size() > 0 ? responses.stream().filter(r -> !r.success()).toString() : " internal reservation.";
		logger.info("Could not aquire lock, failed at".concat(failedAt));
	}

	public void retry() {
		logger.info(id.toString().concat(": Retrying"));
	}
}
