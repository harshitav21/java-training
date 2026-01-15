package assignment1;

import java.util.Scanner;
import java.util.InputMismatchException;

public class Question2 {

    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in)) {
            int noOfStudents = 0;

            while (true) {
                System.out.print("Enter number of students: ");
                try {
                    noOfStudents = sc.nextInt();
                    if (noOfStudents <= 0) {
                        System.out.println("Number of students must be greater than 0. Try again!");
                    } else {
                        break; // valid input, exit loop
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter an integer.");
                    sc.next(); // consume invalid input
                }
            }

            int[] grades = new int[noOfStudents];
            int sum = 0;

            for (int i = 0; i < noOfStudents; i++) {
                int grade = -1;
                while (true) {
                    System.out.print("Enter grade for Student " + (i + 1) + " (0-100): ");
                    try {
                        grade = sc.nextInt();
                        if (grade < 0 || grade > 100) {
                            System.out.println("Invalid grade. Must be between 0 and 100.");
                        } else {
                            break; 
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter an integer.");
                        sc.next(); 
                    }
                }
                grades[i] = grade;
                sum += grade;
            }

            double average = (double) sum / noOfStudents;
            System.out.println("Average grade: " + average);

        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }

    }
}
