package question5;

public class InvalidCountryException extends Exception{
    public InvalidCountryException() {
        super("Invalid country provided.");
    }

    public InvalidCountryException(String message) {
        super(message);
    }
}
