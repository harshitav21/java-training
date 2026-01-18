package assignment2.Question5;

public class SalariedEmployee extends Employee{
	private double monthlySalary;

    public SalariedEmployee(int empId, String name, double monthlySalary) {
        super(name, empId);
        this.monthlySalary = monthlySalary;
    }
    
    @Override
    public double getPayment() {
        return monthlySalary;
    }

    @Override
    public String toString() {
        return "Salaried Employee: " + name + ", Payment: Rs. " + getPayment();
    }

}
