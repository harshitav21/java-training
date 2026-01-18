package assignment2.Question4;

public abstract class Employee {
	protected String name;
    protected int employeeId;

    public Employee(String name, int employeeId) {
        this.name = name;
        this.employeeId = employeeId;
    }

    public abstract double getPayment();
    
    public String getName() {
        return name;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void display() {
        System.out.println("Employee ID: " + employeeId + ", Name: " + name);
    }
}
