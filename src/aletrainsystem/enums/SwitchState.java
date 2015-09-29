package aletrainsystem.enums;

public enum SwitchState {
	
	THROUGH(0), DIVERT(-180);
	
	private int rotationAngle;
	private SwitchState(int rotationAngle){
		this.rotationAngle = rotationAngle;
	}
	
	public int getAngle(){
		return rotationAngle;
	}
}
