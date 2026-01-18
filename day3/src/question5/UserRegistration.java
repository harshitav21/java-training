package question5;

public class UserRegistration {
	
	 public void registerUser(String username, String userCountry) throws InvalidCountryException {
	        if(!userCountry.equalsIgnoreCase("India")) {
	        throw new InvalidCountryException("Sorry " + username + ", registration is allowed only for users from India.");
	    } else {
	        System.out.println("User registration done successfully for " + username + "!");
	    }
	}
}
