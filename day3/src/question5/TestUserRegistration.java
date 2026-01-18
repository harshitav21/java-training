package question5;

import java.util.Scanner;

public class TestUserRegistration {
	public static void main(String[] args) {
        UserRegistration reg = new UserRegistration();
        Scanner sc = new Scanner(System.in);

        while(true) {
            System.out.print("\nEnter username: ");
            String username = sc.nextLine();

            System.out.print("Enter country: ");
            String country = sc.nextLine();

            try {
                reg.registerUser(username, country);
            } catch (InvalidCountryException e) {
                System.out.println("Registration failed: " + e.getMessage());
            }

            System.out.print("Do you want to register another user? (yes/no): ");
            String choice = sc.nextLine();
            if(choice.equalsIgnoreCase("no")) {
                System.out.println("Exiting program...");
                break;
            }
        }

        sc.close();
    }
}
