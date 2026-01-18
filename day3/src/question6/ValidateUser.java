package question6;

public class ValidateUser {
	
	public static void validate(String name, int age) throws InvalidAgeException{
		if(age < 18 || age >= 60) {
			throw new InvalidAgeException("Age must be between 18 and 59.");
		}
		System.out.println("Input: "+ name + " " + age);
		System.out.println("Output: Name: " + name + ", Age: " + age);
	}
	
	public static void main(String[] args) {
		if(args.length > 2) {
			System.out.println("Please provide name and age as command-line arguments");
            System.out.println("Example: Sasha 25");
            return;
		}
		
		String name = args[0];
		int age = 0;
		try {
			age = Integer.parseInt(args[1]);
		}catch(NumberFormatException e) {
			System.out.println("Age must be a valid number.");
			return;
		}
		
		try {
            validate(name, age);
        }catch(InvalidAgeException e) {
            System.out.println("InvalidAgeException: " + e.getMessage());
        }
		
	}
}
