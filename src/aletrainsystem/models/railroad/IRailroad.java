package aletrainsystem.models.railroad;

import aletrainsystem.models.locking.Lockable;

public interface IRailroad {
	public static final String PROPERTYNAME = "MAP_FILE";
		
	public boolean isStation(String railComponentId);
	
	public boolean isStation(RegularLeg railLeg);
	
	public Point findOrAddPoint(String railComponentId);
	
	public StartLeg getRailSystemStartLeg();
	
	public Lockable getLockableResource(String id);
}
