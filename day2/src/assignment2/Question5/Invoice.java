package assignment2.Question5;

public class Invoice implements Payable{
	private int invoiceId;
    private String itemDescription;
    private int quantity;
    private double pricePerUnit;

    public Invoice(int invoiceId, String itemDescription, int quantity, double pricePerUnit) {
        this.invoiceId = invoiceId;
        this.itemDescription = itemDescription;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
    }
    
    @Override
    public double getPayment() {
        return quantity * pricePerUnit;
    }
    public String toString() {
        return "Invoice ID: " + invoiceId + ", Item: " + itemDescription +", Payment: Rs. " + getPayment();
    }
}
