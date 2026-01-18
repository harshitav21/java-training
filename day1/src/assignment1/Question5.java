package assignment1;

import java.util.Scanner;

//Q5. Next Date Calculator
//Requirements
//
//Input day, month, year from the user.
//Validate the date.
//Compute the next day’s date manually.
//Display both dates.


public class Question5 {
		 static boolean isLeapYear(int year) {
			if(year % 400 == 0) return true;
			if(year % 100 == 0) return false;
			return (year % 4 == 0);
		}
		
		 static int daysInMonth(int month, int year) {
			switch(month) {
			case 1: case 3: case 5: case 7: case 8: case 10: case 12:
				return 31;
			case 4: case 6: case 9: case 11: 
				return 30;
			case 2: return isLeapYear(year) ? 29 : 28;
			default: return -1;
			}
		}
		
		static boolean isValidDate(int day, int month, int year) {
			if(year <= 0) return false;
			if(month < 1 || month > 12) return false;
			
			int maxDays = daysInMonth(month, year);
			if(day < 1 || day > maxDays) return false;
			
			return true;
		}
		
		@SuppressWarnings("resource")
		public static void main(String [] args) {
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter day: ");
			int day = sc.nextInt();
			
			System.out.println("Enter month: ");
			int month = sc.nextInt();
			
			System.out.println("Enter year: ");
			int year = sc.nextInt();
			
			if(!isValidDate(day, month, year)) {
				System.out.println("Invalid Date!");
				return;
			}
			
			int maxDays = daysInMonth(month, year);
			
			if(day < maxDays) {
				day++;
			}else {
				day = 1;
				if(month < 12) {
					month++;
				}
				else {
					month = 1;
					year++;
				}
			}
			
			System.out.println("Next Date: " + day + "/" + month + "/" + year);
			sc.close();
		}
}
