package question4;

public class Product {
	private int productId;
	private String productName;
	private double productPrice;
	public Product(int productId, String productName, double productPrice) {
		this.setProductId(productId);
		this.productName = productName;
		this.productPrice = productPrice;
	}
	
	 public double getProductPrice() {
		 return productPrice;
	 }
	
	@Override
	public String toString() {
		return productName + " (" + productPrice + " ₹)";
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}
	
}
