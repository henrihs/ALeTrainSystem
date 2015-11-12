package aletrainsystem.lockhandler;

import java.util.Set;

import aletrainsystem.lockcoordinator.LockCoordinator;
import aletrainsystem.lockparticipant.LockParticipant;
import aletrainsystem.models.TrainId;
import aletrainsystem.models.locking.CoordinatorInitParams;
import aletrainsystem.models.locking.Lockable;
import aletrainsystem.models.locking.Request;
import aletrainsystem.models.locking.RequestType;
import aletrainsystem.models.locking.Response;
import aletrainsystem.models.locking.TransactionId;
import no.ntnu.item.arctis.runtime.Block;
import ntnu.no.rabbitamqp.util.Message;

public class LockHandler extends Block {
	
	public long counter = 10000;
	public TrainId trainId;
	public aletrainsystem.models.locking.Request request;
	
	public void setIds(TrainId id) {
		this.trainId = id;
		setProperty(LockCoordinator.ENTITY_ID, id);
		setProperty(LockParticipant.ENTITY_ID, id);
	}

	public void updateParticipants(Set<TrainId> participants) {
		setProperty(LockCoordinator.PARTICIPANTS_KEY, participants);
	}

	public CoordinatorInitParams generateCoordinatorInitParams(Set<Lockable> objectsToLock) {
		String s = trainId.toString().concat(String.valueOf(++counter));
		TransactionId transactionId = new TransactionId(s);
		return new CoordinatorInitParams(transactionId, objectsToLock);
	}

//	public Message generateResponseMessage(Response r) {
//		return new Message("trains.".concat(r.requester().toString()), r);
//	}
//	
//	public Message generateRequestMessage(Request r) {
//		return new Message("trains.common", r);
//	}

	// Do not edit this constructor.
	public LockHandler() {
	}

	public boolean isInternalRequest() {
		return request.collector().equals(trainId);
	}

	public Request generateReleaseRequest(Set<Lockable> resourcesToRelease) {
		String id = trainId.toString().concat(String.valueOf(++counter));
		return new Request(new TransactionId(id), trainId, resourcesToRelease, RequestType.RELEASE);
	}
}
