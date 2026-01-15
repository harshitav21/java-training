package assignment1;

public class Question1 {
	public static void main(String[] args) {
		int[] fiboArr = new int[20];
		
		// Initialize first two Fibonacci numbers
		fiboArr[0] = 0;
		fiboArr[1] = 1;
		int sum = fiboArr[0] + fiboArr[1];
		
		for(int i=2; i<20; i++) {
			fiboArr[i] = fiboArr[i-1] + fiboArr[i-2];
			sum += fiboArr[i];
		}
		System.out.println("The first 20 Fibonacci elements are: ");
		for(int num : fiboArr) {
			System.out.print(num + " ");
		}
		System.out.println();
		System.out.println("Average is: " + sum/20);
		
	}
}
