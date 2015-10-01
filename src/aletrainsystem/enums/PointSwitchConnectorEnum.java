package aletrainsystem.enums;

public enum IntersectionConnectorEnum {
	
	THROUGH(0,"t"), DIVERT(-180,"d"), ENTRY(0,"e");
	
	private int rotationAngle;
	private String shorthand;
	private IntersectionConnectorEnum(int rotationAngle, String shorthand){
		this.rotationAngle = rotationAngle;
		this.shorthand = shorthand;
	}
	
	public int angle(){
		return rotationAngle;
	}
	
	public String shorthand(){
		return shorthand;
	}
	
}