package assignment2.Question4;

public class HourlyEmployee extends Employee{
	private int hoursWorked;
    private double hourlyRate;

    public HourlyEmployee(String name, int employeeId, int hoursWorked, double hourlyRate) {
        super(name, employeeId);
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double getPayment() {
        return hoursWorked * hourlyRate;
    }
}
