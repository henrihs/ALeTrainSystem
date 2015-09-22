package ntnu.no.aletrainsystem.pointswitch;

public enum SwitchState {
	
	through(0), divert(-180);
	
	private int rotationAngle;
	private SwitchState(int rotationAngle){
		this.rotationAngle = rotationAngle;
	}
	
	public int getAngle(){
		return rotationAngle;
	}
}
