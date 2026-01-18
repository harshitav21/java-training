package question7;

import java.util.Scanner;

public class DivisionProgram {
	public static void main(String[] args) {
		try(Scanner sc = new Scanner(System.in)) {
			System.out.print("\nEnter Dividend: ");
			int a = sc.nextInt();
			System.out.print("\nEnter Divisor: ");
			int b = sc.nextInt();
			int quotient = a/b;
			System.out.println("The Quotient of "+ a + "/" + b + " is: " + quotient);
			
		}catch(ArithmeticException e) {
			System.out.println("DivideByZeroException: " + e.getMessage());
		}finally {
			System.out.println("Inside finally block.");
		}
	}
}
