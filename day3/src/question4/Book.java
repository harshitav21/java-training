package question4;

public class Book {
	private int id;
	private String isbn;
	private String title;
	private String author;
	private double price;   
	private int stock;  
	
	public Book(int id, String isbn, String title, String author, double price) {
	    this.id = id;
	    this.isbn = isbn;
	    this.title = title;
	    this.author = author;
	    this.price = price;
	    this.stock = 10; 
	}
	

	public int getId() { return id; }
	public String getIsbn() { return isbn; }
	public String getTitle() { return title; }
	public String getAuthor() { return author; }
	public double getPrice() { return price; }
	public int getStock() { return stock; }
	
	public void sell(int noOfCopies) throws NotSufficientBookException {
	    if(noOfCopies > stock) {
	        throw new NotSufficientBookException("Not enough copies of " + title);
	    }
	    stock -= noOfCopies;
	}
	
	public void purchase(int noOfCopies) {
	    stock += noOfCopies;
	}
	
	public String toString() {
	    return id + " : " + isbn + " : " + title + " : " + author + " : Price=" + price + " : Stock=" + stock;
	}
}
