package ntnu.no.aletrainsystem.exceptions;

public class MotorException extends Exception {
	public MotorException(String message) {
		super(message);
		this.fillInStackTrace();
	}
}
