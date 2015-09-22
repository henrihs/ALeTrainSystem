package ntnu.no.aletrainsystem.servo;

import no.ntnu.item.arctis.runtime.Block;
import ntnu.no.aletrainsystem.pointswitch.SwitchState;
import lejos.hardware.motor.NXTRegulatedMotor;

public class Servo extends Block {

	public NXTRegulatedMotor motor;

	public void rotateServoTo(SwitchState state) {
		motor.setSpeed(1080);
		motor.rotateTo(state.getAngle());
	}
	
	public SwitchState getState() throws SwitchStateException{
		int angle = (int) motor.getPosition();
		switch (Math.abs(angle)) {
		case 0:
			return SwitchState.through;
		case 180:
			return SwitchState.divert;
		default:
			throw new SwitchStateException("Angle of servo could not be interpreted");
		}
	}

	public void setToInitPosition() {
		motor.rotateTo(SwitchState.through.getAngle());
	}

}
