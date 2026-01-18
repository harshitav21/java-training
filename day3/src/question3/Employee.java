package question3;

import java.io.Serializable;

public class Employee implements Serializable{
 private int id;
    private String name;
    private Address address;
    private transient double salary;

    public Employee(int id, String name, Address address, double salary) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.salary = salary;
    }

    public void display() {
        System.out.println("Employee ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("City: " + address.getCity());
        System.out.println("State: " + address.getState());
        System.out.println("Salary: " + salary);
        System.out.println("-------------------------");
    }
}
