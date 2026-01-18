package assignment2.Question4;

import java.util.ArrayList;

public class EmployeeApp {
	public static void main(String[] args) {
	 ArrayList<Employee> employees = new ArrayList<>();

        employees.add(new SalariedEmployee("Yash", 101, 100000));
        employees.add(new HourlyEmployee("Amit", 102, 40, 25));
        employees.add(new CommissionEmployee("Harshita", 103, 20000, 10));

        System.out.println("--- Weekly Salary Details ---");

        for (Employee emp : employees) {
        	System.out.println(
                    "Employee ID: " + emp.getEmployeeId() +
                    ", Name: " + emp.getName() +
                    ", Weekly Payment: ₹" + emp.getPayment()
            );
        }
	}
}
