package aletrainsystem.lockparticipant;

import aletrainsystem.models.TrainId;
import aletrainsystem.models.locking.Lockable;
import aletrainsystem.models.locking.Request;
import aletrainsystem.models.locking.RequestType;
import aletrainsystem.models.locking.Response;
import no.ntnu.item.arctis.runtime.Block;

public class LockParticipant extends Block {
		
	public Request request;
	public RequestType requestType;

	public aletrainsystem.models.locking.Response response;

	public aletrainsystem.models.railroad.Railroad resources;

	public aletrainsystem.models.TrainId participantId;

	public LockParticipant() {
	}

	public Response generateResponse() {
		boolean success = false;
		if (isReserve())
			success = isReservedForCoordinator();
		else if (isPerform())
			success = isLockedForCoordinator();
		
		return new Response(participantId, request, success);
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
		return response.respondent().equals(response.collector());
	}

	public void reserveLock() {
		for (String lockableId : request.lockableIDs()) {
			Lockable localInstance = getLocalInstance(lockableId);
			localInstance.reserveLock(request.collector());
			updateLocalInstance(localInstance);
		}
	}

	public boolean isReservedForCoordinator() {
		for (String lockableId : request.lockableIDs()) {
			Lockable localInstance = getLocalInstance(lockableId);
			TrainId lockOwner = localInstance.checkReservation();
			if (lockOwner == null || !lockOwner.equals(request.collector())) {
				return false;
			}
		}
		
		return true;
	}

	public boolean isReservedForOther() {
		for (String lockableId : request.lockableIDs()) {
			Lockable localInstance = getLocalInstance(lockableId);
			TrainId lockOwner = localInstance.checkReservation();
			if (lockOwner != null && !lockOwner.equals(request.collector()))
				return true;
		}
		
		return false;
	}

	public void lock() {
		for (String lockableId : request.lockableIDs()) {
			Lockable localInstance = getLocalInstance(lockableId);
			if (localInstance.checkReservation() == null && localInstance.checkLock() == null ) {
				localInstance.performLock(request.collector());
			}
			
			updateLocalInstance(localInstance);
		}
	}

	public void abortReservation() {
		for (String lockableId : request.lockableIDs()) {
			Lockable localInstance = getLocalInstance(lockableId);
			TrainId lockOwner = localInstance.checkReservation();
			if (lockOwner.equals(request.collector())) {
				localInstance.releaseReservation();
			}
			
			updateLocalInstance(localInstance);
		}
	}

	public boolean isLockedForCoordinator() {
		for (String lockableId : request.lockableIDs()) {
			Lockable localInstance = getLocalInstance(lockableId);
			TrainId lockOwner = localInstance.checkLock();
			if (lockOwner == null || !lockOwner.equals(request.collector())) {
				return false;
			}
		}
		return true;
	}

	public void releaseLock() {
		for (String id : request.lockableIDs()) {
			Lockable localInstance = getLocalInstance(id);
			localInstance.unLock();
			updateLocalInstance(localInstance);
		}
	}

	public void logInit() {
		logger.info("Initialized");
	}
	
	private synchronized Lockable getLocalInstance(String id) {
		return resources.getLockableResource(id);
	}
	
	private synchronized void updateLocalInstance(Lockable lockable) {
		resources.updateLockableResource(lockable);
	}

	public void received() {
		logger.info(
				request.transactionId().toString().
				concat(": Received ").
				concat(request.type().toString()).
				concat(" for ").
				concat(request.lockableIDs().toString())
				);
	}
}
