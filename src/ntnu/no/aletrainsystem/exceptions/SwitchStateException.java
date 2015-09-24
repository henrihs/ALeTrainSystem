package ntnu.no.aletrainsystem.exceptions;

public class SwitchStateException extends Exception {

	public SwitchStateException(String message) {
		super(message);
		this.fillInStackTrace();
	}

}
