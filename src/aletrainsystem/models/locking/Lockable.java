package aletrainsystem.models.locking;

import aletrainsystem.models.TrainId;

public interface Lockable {
	
	public Object id();
	
	public TrainId checkReservation();
	public void reserveLock(TrainId owner);
	public void releaseReservation();
	
	public TrainId checkLock();
	public void performLock(TrainId owner);
	public void unLock();
}
