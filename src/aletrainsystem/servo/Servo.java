package aletrainsystem.servo;

import no.ntnu.item.arctis.runtime.Block;
import aletrainsystem.enums.MotorPort;
import aletrainsystem.enums.PointConnectorEnum;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;

public class Servo extends Block {

	public EV3LargeRegulatedMotor motor;

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

	public EV3LargeRegulatedMotor getMotor(MotorPort port) {
		return new EV3LargeRegulatedMotor(LocalEV3.get().getPort(port.toString()));		
	}

	public void init() {
		motor.resetTachoCount();
	}
}
