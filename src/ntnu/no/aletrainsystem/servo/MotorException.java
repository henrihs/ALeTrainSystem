package ntnu.no.aletrainsystem.servo;

public class MotorException extends Exception {
	public MotorException(String message) {
		super(message);
		this.fillInStackTrace();
	}
}
