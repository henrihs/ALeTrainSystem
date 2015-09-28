package ntnu.no.aletrainsystem.servo;

import no.ntnu.item.arctis.runtime.Block;
import ntnu.no.aletrainsystem.enums.MotorPort;
import ntnu.no.aletrainsystem.enums.SwitchState;
import lejos.hardware.motor.NXTRegulatedMotor;

public class Servo extends Block {

	public NXTRegulatedMotor motor;

	public SwitchState rotateServoTo(SwitchState state) {
		motor.setSpeed(1080);
		motor.rotateTo(state.getAngle());
		return state;
	}

	public SwitchState getState() {
		int angle = (int) motor.getPosition();
		switch (Math.abs(angle)) {
		case 0:
			return SwitchState.THROUGH;
		case 180:
			return SwitchState.DIVERT;
		default:
			logger.error("Position of servo could not be interpreted");
			return SwitchState.THROUGH;
		}
	}

	public void setToInitPosition() {
		motor.rotateTo(SwitchState.THROUGH.getAngle());
	}

	public NXTRegulatedMotor getMotor(MotorPort port) {
		switch (port) {
		case A:
			return lejos.hardware.motor.Motor.A;
		case B:
			return lejos.hardware.motor.Motor.B;
		case C:
			return lejos.hardware.motor.Motor.C;
		case D:
			return lejos.hardware.motor.Motor.D;
		default:
			logger.error("Missing case for enum ".concat(port.name()));
			return null;
		}
	}

}
