package assignment2.Question2;

public class BookStoreApp {
	 public static void main(String[] args) {

	        BookStore store = new BookStore();

	        store.order("111", 5); 
	        store.order("222", 3);

	        store.display();
	        System.out.println();

	        // Order existing book
	        store.order("111", 2);
	        System.out.println();
	        store.display();

	        // Sell books
	        store.sell("Java Basics", 2);
	        store.sell("Java Basics", 10);
	        System.out.println();

	        store.display();
	    }
}
