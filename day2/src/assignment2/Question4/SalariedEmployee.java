package assignment2.Question4;

public class SalariedEmployee extends Employee {
	private double weeklySalary;

    public SalariedEmployee(String name, int employeeId, double weeklySalary) {
        super(name, employeeId);
        this.weeklySalary = weeklySalary;
    }

    @Override
    public double getPayment() {
        return weeklySalary;
    }
}
