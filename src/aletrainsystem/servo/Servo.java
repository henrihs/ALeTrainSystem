package aletrainsystem.servo;

import no.ntnu.item.arctis.runtime.Block;
import aletrainsystem.enums.MotorPort;
import aletrainsystem.enums.PointConnectorEnum;
import lejos.hardware.motor.NXTRegulatedMotor;

public class Servo extends Block {

	public NXTRegulatedMotor motor;

	public PointConnectorEnum rotateServoTo(PointConnectorEnum state) {
		motor.setSpeed(1080);
		motor.rotateTo(state.angle());
		return state;
	}

	public PointConnectorEnum getState() {
		int angle = (int) motor.getPosition();
		switch (Math.abs(angle)) {
		case 0:
			return PointConnectorEnum.THROUGH;
		case 180:
			return PointConnectorEnum.DIVERT;
		default:
			logger.error("Position of servo could not be interpreted");
			return PointConnectorEnum.THROUGH;
		}
	}

	public void setToInitPosition() {
		motor.rotateTo(PointConnectorEnum.THROUGH.angle());
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
