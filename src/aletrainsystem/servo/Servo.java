package aletrainsystem.servo;

import no.ntnu.item.arctis.runtime.Block;
import aletrainsystem.enums.MotorPort;
import aletrainsystem.enums.IntersectionConnectorEnum;
import lejos.hardware.motor.NXTRegulatedMotor;

public class Servo extends Block {

	public NXTRegulatedMotor motor;

	public IntersectionConnectorEnum rotateServoTo(IntersectionConnectorEnum state) {
		motor.setSpeed(1080);
		motor.rotateTo(state.angle());
		return state;
	}

	public IntersectionConnectorEnum getState() {
		int angle = (int) motor.getPosition();
		switch (Math.abs(angle)) {
		case 0:
			return IntersectionConnectorEnum.THROUGH;
		case 180:
			return IntersectionConnectorEnum.DIVERT;
		default:
			logger.error("Position of servo could not be interpreted");
			return IntersectionConnectorEnum.THROUGH;
		}
	}

	public void setToInitPosition() {
		motor.rotateTo(IntersectionConnectorEnum.THROUGH.angle());
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
