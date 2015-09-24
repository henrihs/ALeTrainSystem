package ntnu.no.aletrainsystem.servo;

import no.ntnu.item.arctis.runtime.Block;
import ntnu.no.aletrainsystem.enums.MotorPort;
import ntnu.no.aletrainsystem.enums.SwitchState;
import ntnu.no.aletrainsystem.exceptions.MotorException;
import ntnu.no.aletrainsystem.exceptions.SwitchStateException;
import lejos.hardware.motor.NXTRegulatedMotor;

public class Servo extends Block {

	public NXTRegulatedMotor motor;

	public void rotateServoTo(SwitchState state) {
		motor.setSpeed(1080);
		motor.rotateTo(state.getAngle());
	}

	public SwitchState getState() throws SwitchStateException {
		int angle = (int) motor.getPosition();
		switch (Math.abs(angle)) {
		case 0:
			return SwitchState.THROUGH;
		case 180:
			return SwitchState.DIVERT;
		default:
			throw new SwitchStateException("Position of servo could not be interpreted");
		}
	}

	public void setToInitPosition() {
		motor.rotateTo(SwitchState.THROUGH.getAngle());
	}

	public NXTRegulatedMotor getMotor(MotorPort port) {
		return port.getMotor();
	}

}
