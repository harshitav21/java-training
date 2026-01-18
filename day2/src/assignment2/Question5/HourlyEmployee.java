package assignment2.Question5;

public class HourlyEmployee extends Employee{
	private int hoursWorked;
    private double ratePerHour;

    public HourlyEmployee(int empId, String name, int hoursWorked, double ratePerHour) {
        super(name, empId);
        this.hoursWorked = hoursWorked;
        this.ratePerHour = ratePerHour;
    }

    @Override
    public double getPayment() {
        return hoursWorked * ratePerHour;
    }

    @Override
    public String toString() {
        return "Hourly Employee: " + name + ", Payment: Rs." + getPayment();
    }
}
