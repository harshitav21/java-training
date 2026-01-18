package assignment2.Question5;

public abstract class Employee implements Payable{
	 protected String name;
    protected int employeeId;

    public Employee(String name, int employeeId) {
        this.name = name;
        this.employeeId = employeeId;
    }

    public void display() {
        System.out.println("Employee ID: " + employeeId + ", Name: " + name);
    }
}
