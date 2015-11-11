package aletrainsystem.lockparticipant;

import aletrainsystem.models.TrainId;
import aletrainsystem.models.locking.Lockable;
import aletrainsystem.models.locking.Request;
import aletrainsystem.models.locking.RequestType;
import aletrainsystem.models.locking.Response;
import aletrainsystem.models.railroad.IRailroad;
import no.ntnu.item.arctis.runtime.Block;

public class LockParticipant extends Block {
	
	public final static String ENTITY_ID = "id";
	
	public IRailroad railroad;
	public Request request;
	public RequestType requestType;

	public aletrainsystem.models.railroad.IRailroad resources;

	public aletrainsystem.models.locking.Response response;

	public LockParticipant() {
	}

	public Response generateResponse() {
		TrainId participant = null;
		if (hasProperty(ENTITY_ID)) {
			Object c = getProperty(ENTITY_ID);
			if (c != null && c instanceof Class<?>) {
				participant = (TrainId) c;
			}
		}
				
		boolean success = false;
		if (isReserve())
			success = isReservedForCoordinator();
		else if (isPerform())
			success = isLockedForCoordinator();
		
		return new Response(participant, request, success);
	}

	public boolean isReserve() {
		return request.type() == RequestType.RESERVE;
	}

	public boolean isPerform() {
		return request.type() == RequestType.PERFORM;
	}

	public boolean isAbort() {
		return request.type() == RequestType.ABORT;
	}

	public boolean isRelease() {
		return request.type() == RequestType.RELEASE;
	}
	
	public boolean isInternalResponse() {
		return response.responder().equals(response.requester());
	}

	public void reserveLock() {
		for (Lockable lockable : request.objectsToLock()) {
			Lockable localInstance = railroad.getLockableResource(lockable.id().toString());
			TrainId lockOwner = localInstance.checkLock();
			if (lockOwner == null || lockOwner.equals(request.coordinator())) {
				localInstance.reserveLock(request.coordinator());
				lockable.reserveLock(request.coordinator());
			}
		}
	}

	public boolean isReservedForCoordinator() {
		for (Lockable lockable : request.objectsToLock()) {
			Lockable localInstance = railroad.getLockableResource(lockable.id().toString());
			TrainId lockOwner = localInstance.checkReservation();
			if (!lockOwner.equals(request.coordinator())) {
				return false;
			}
		}
		return true;
	}

	public boolean isReservedForOther() {
		for (Lockable lockable : request.objectsToLock()) {
			Lockable localInstance = railroad.getLockableResource(lockable.id().toString());
			TrainId lockOwner = localInstance.checkLock();
			if (lockOwner != null || !lockOwner.equals(request.coordinator()))
				return true;
		}
		return false;
	}

	public void lock() {
		for (Lockable lockable : request.objectsToLock()) {
			Lockable localInstance = railroad.getLockableResource(lockable.id().toString());
			TrainId lockOwner = localInstance.checkLock();
			if (lockOwner == null || lockOwner.equals(request.coordinator())) {
				localInstance.performLock(request.coordinator());
				lockable.performLock(request.coordinator());
			}
		}
	}

	public void abortReservation() {
		for (Lockable lockable : request.objectsToLock()) {
			Lockable localInstance = railroad.getLockableResource(lockable.id().toString());
			TrainId lockOwner = localInstance.checkReservation();
			if (lockOwner == null || lockOwner.equals(request.coordinator())) {
				localInstance.releaseReservation();
				lockable.releaseReservation();
			}
		}
	}

	public boolean isLockedForCoordinator() {
		for (Lockable lockable : request.objectsToLock()) {
			Lockable localInstance = railroad.getLockableResource(lockable.id().toString());
			TrainId lockOwner = localInstance.checkLock();
			if (!lockOwner.equals(request.coordinator())) {
				return false;
			}
		}
		return true;
	}

	public void releaseLock() {
	}
}