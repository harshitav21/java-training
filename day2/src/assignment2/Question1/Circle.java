package assignment2.Question1;

public class Circle {
	private double radius;
	private String color;
	
	public Circle() {    
	      radius = 1.0;
	      color = "red";
	}
	public Circle(double radius) {           
	      this.radius = radius;
	      color = "red";
	}
	public Circle(double radius, String color) {
		this.radius = radius;
		this.color = color;
	}
	public double getRadius() {
		return radius;
	}
	public double getArea() {
		double area = radius * radius * Math.PI;;
		return area;
	}
	
	public String getColor() {
		return color;
	}
	
	
}
