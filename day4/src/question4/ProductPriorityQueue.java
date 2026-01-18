package question4;

import java.util.Comparator;
import java.util.PriorityQueue;

public class ProductPriorityQueue {
	
	public static void main(String[] args) {
		Comparator<Product> priceComparatorAsc = Comparator.comparingDouble(Product::getProductPrice);
		
		Comparator<Product> priceComparatorDesc = (p1, p2) -> Double.compare(p2.getProductPrice(), p1.getProductPrice());
		
		PriorityQueue<Product> pq = new PriorityQueue<>(priceComparatorAsc);

		pq.add(new Product(101, "Pen", 10.0));
	    pq.add(new Product(102, "Notebook", 25.0));
	    pq.add(new Product(103, "Bag", 400.0));
	    pq.add(new Product(104, "Laptop", 60000.0));
	    pq.add(new Product(105, "Mouse", 500.0));

	    System.out.println("Processing products by ascending price:");
	    while (!pq.isEmpty()) {
	        System.out.println("Processing product: " + pq.poll());
	    }

	    System.out.println("\nProcessing products by descending price:");

	    PriorityQueue<Product> pqDesc = new PriorityQueue<>(priceComparatorDesc);

	    pqDesc.add(new Product(101, "Pen", 10.0));
	    pqDesc.add(new Product(102, "Notebook", 25.0));
	    pqDesc.add(new Product(103, "Bag", 400.0));
	    pqDesc.add(new Product(104, "Laptop", 60000.0));
	    pqDesc.add(new Product(105, "Mouse", 500.0));

	    while (!pqDesc.isEmpty()) {
	        System.out.println("Processing product: " + pqDesc.poll());
	    }
	}

	
}
