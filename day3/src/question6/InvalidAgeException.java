package question6;

public class InvalidAgeException extends Exception{
	public InvalidAgeException() {
		super("Invalid age provided.");
	}
	public InvalidAgeException(String message) {
		super(message);
	}
}
