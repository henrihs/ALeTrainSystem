package aletrainsystem.lockcoordinator;

import java.util.Set;

import aletrainsystem.models.TrainId;
import aletrainsystem.models.locking.CoordinatorInitParams;
import aletrainsystem.models.locking.Request;
import aletrainsystem.models.locking.RequestType;
import aletrainsystem.models.locking.Response;
import no.ntnu.item.arctis.runtime.Block;

public class LockCoordinator extends Block {
	public static final String PARTICIPANTS_KEY = "participants";
	public static final String ENTITY_ID = "id";
	public Set<Response> responses;
	public aletrainsystem.models.locking.TransactionId id;
	public java.util.Set<aletrainsystem.models.locking.Lockable> objectsToLock;
	public aletrainsystem.models.locking.Request request;

	public static String getAlias(CoordinatorInitParams params) {
		return params.id().toString();
	}

	public static String getAlias(Response r) {
		return r.transactionId().toString();
	}

	public void saveInitParams(CoordinatorInitParams params) {
		this.id = params.id();
		this.objectsToLock = params.objectsToLock();
	}

	public Request generateReservationRequest() {
		TrainId coordinator = null;
		if (hasProperty(ENTITY_ID)) {
			Object c = getProperty(ENTITY_ID);
			if (c != null && c instanceof Class<?>) {
				coordinator = (TrainId) c;
			}
		}
		Request request = new Request(id, coordinator, objectsToLock, RequestType.RESERVE);
		return request;
	}

	public boolean lockAcquired(Response response) {
		return response.success();
	}

	public void saveResponse(Response r) {
		responses.add(r);
	}

	public boolean waitingForResponses() {
		Set<TrainId> participants = null;
		if (hasProperty(PARTICIPANTS_KEY)) {
			Object p = getProperty(PARTICIPANTS_KEY);
			if (p != null && p instanceof Set<?>) {
				participants = (Set<TrainId>) p;
			}
		}
		int matches = 0;
		for (Response response : responses) {
			for (TrainId participant : participants) {
				if (response.responder().equals(participant))
					matches++;
			}
		}
		if (matches == participants.size())
			return true;
		return false;
	}

	public Request changeToPerformRequest(Request r) {
		r.type(RequestType.PERFORM);
		return r;
	}
}
