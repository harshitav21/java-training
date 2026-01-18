package assignment2.Question5;

import java.util.ArrayList;

public class PaymentApp {
	public static void main(String[] args) {

	 ArrayList<Payable> payments = new ArrayList<>();

        payments.add(new SalariedEmployee(101, "Amit", 50000));
        payments.add(new HourlyEmployee(102, "Riya", 120, 300));
        
        payments.add(new Invoice(1, "Laptop", 2, 45000));
        payments.add(new Invoice(2, "Printer", 1, 15000));

        for (Payable p : payments) {
            System.out.println(p);
        }
    }

}
