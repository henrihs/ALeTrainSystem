package ntnu.no.aletrainsystem.servo;

public class SwitchStateException extends Exception {

	public SwitchStateException(String message) {
		super(message);
		this.fillInStackTrace();
	}

}
