package assignment2.Question1;

public class TestCircle {
	public static void main(String[] args) {

        Circle c1 = new Circle();
        Circle c2 = new Circle(3.5);
        Circle c3 = new Circle(5.0, "Blue");

        System.out.println("----- Circle Details -----");

        displayCircleDetails(c1);
        displayCircleDetails(c2);
        displayCircleDetails(c3);
    }

    public static void displayCircleDetails(Circle c) {

        double radius = c.getRadius();
        double area = c.getArea();
        double circumference = 2 * Math.PI * radius;

        System.out.println("Radius: " + radius);
        System.out.printf("The Area is: %.2f%n", area);
        System.out.printf("The Circumference is: %.2f%n", circumference);
        System.out.println("--------------------------");
    }
}

