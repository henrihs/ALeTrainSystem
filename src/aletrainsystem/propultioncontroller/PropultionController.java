package aletrainsystem.propultioncontroller;

import item.ntnu.no.lemip.colorsensor.ColorSensor;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.Port;
import no.ntnu.item.arctis.runtime.Block;

public class PropultionController extends Block {
	private Port A = LocalEV3.get().getPort("A");
	public EV3MediumRegulatedMotor motor = new EV3MediumRegulatedMotor(A);

	public int changeSpeed(int speed) {
		motor.rotateTo(-15 * speed, false);
		return speed;
	}

	public void calibrateController(int degrees) {
		motor.rotate(degrees);
	}

	public void resetTachoCount() {
		motor.resetTachoCount();
		motor.setSpeed(900);
		motor.setAcceleration(10000);
	}

	public void stopPod() {
		motor.rotateTo(0, false);
	}
}
