package aletrainsystem.models.railroad;

public interface IRailroad {
	public static final String PROPERTYNAME = "MAP_FILE";
		
	public boolean isStation(String railComponentId);
	
	public Point findOrAddPoint(long railComponentId);
	
	public StartLeg getRailSystemStartLeg();
	
}
