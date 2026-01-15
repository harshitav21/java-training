package assignment1;

import java.util.Scanner;

//2D Array – Pattern Triangle (Pascal-Style)

public class Question4 {
	static void printTriangle(int n) {
		int arr[][] = new int[n][];
		arr[0] = new int[1];
		arr[0][0] = 1;
		
		for(int i=1; i<n; i++) {
			arr[i] = new int[i+1];
			arr[i][0] = 1;
			arr[i][i] = 1;
			
			for(int j=1; j<i; j++) {
				arr[i][j] = arr[i-1][j-1] + arr[i-1][j];
			}	
		}
		
		for(int i=0; i<n; i++) {
			for(int j=0; j<arr[i].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
		
	}
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter height of the triangle: ");
		int n = sc.nextInt();
		printTriangle(n);
		sc.close();
	}
}
