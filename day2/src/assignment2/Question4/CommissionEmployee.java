package assignment2.Question4;



public class CommissionEmployee extends Employee{
	    private double totalSales;
	    private double commissionRate; 

	    public CommissionEmployee(String name, int employeeId, double totalSales, double commissionRate) {
	        super(name, employeeId);
	        this.totalSales = totalSales;
	        this.commissionRate = commissionRate;
	    }

	    @Override
	    public double getPayment() {
	        return (commissionRate * totalSales) / 100;
	    }
}
