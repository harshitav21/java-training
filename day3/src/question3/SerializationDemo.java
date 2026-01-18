package question3;
import java.io.*;

public class SerializationDemo {
  public static void main(String[] args) {

        Employee emp = new Employee(
                101,
                "Harshita",
                new Address("Janakpuri", "Delhi"),
                50000
        );

        // -------- Serialization --------
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("employee.ser"));

            oos.writeObject(emp);
            oos.close();

            System.out.println("Employee object serialized.\n");

        } catch (IOException e) {
            System.out.println("Serialization error: " + e.getMessage());
        }

        // -------- Deserialization --------
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("employee.ser"));

            Employee deserializedEmp = (Employee) ois.readObject();
            ois.close();

            System.out.println("Employee object deserialized:");
            deserializedEmp.display();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Deserialization error: " + e.getMessage());
        }
    }
}
