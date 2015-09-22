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

	public SwitchState getState() throws SwitchStateException {
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

	public NXTRegulatedMotor initializeMotor(String s) throws MotorException {
		switch (s) {
		case "A":
			return lejos.hardware.motor.Motor.A;
		case "B":
			return lejos.hardware.motor.Motor.B;
		case "C":
			return lejos.hardware.motor.Motor.C;
		case "D":
			return lejos.hardware.motor.Motor.D;
		default:
			logger.error("Expected motor A through D, got: ".concat(s));
			throw new MotorException("Expected [A-D], got: ".concat(s));
		}
	}

}
