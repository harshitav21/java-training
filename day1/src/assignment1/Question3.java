package assignment1;
import java.util.Scanner;

public class Question3 {
	public static int[] copyOf(int[] array) {

	    int[] copy = new int[array.length];

	    for (int i = 0; i < array.length; i++) {
	        copy[i] = array[i];
	    }

	    return copy;
	}
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter size of the array: ");
		int size = sc.nextInt();
		
		if(size < 0) {
			System.out.println("Invalid array size, try again!");
			sc.close();
			return;
		}
		
		int[] arr = new int[size];
		System.out.print("Enter " + size + " elements for the array : ");
		for(int i=0; i<arr.length; i++) {
			arr[i] = sc.nextInt();
		}
		
		int[] copiedArr = copyOf(arr);
		
		//Modifying Copied Array
        copiedArr[0] = 99;
		
        System.out.print("Original array: ");
        for (int x : arr) {
            System.out.print(x + " ");
        }
		System.out.println();
        System.out.print("Copied array: ");
        for (int x : copiedArr) {
            System.out.print(x + " ");
        }
        sc.close();
	}
}
