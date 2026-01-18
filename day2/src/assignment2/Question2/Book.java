package assignment2.Question2;



public class Book {
	private String bookTitle;
	private String author;
	private String ISBN;
	private int numOfCopies;
	
	public Book(String bookTitle, String author, String iSBN, int numOfCopies) {
		super();
		this.bookTitle = bookTitle;
		this.author = author;
		ISBN = iSBN;
		setNumOfCopies(numOfCopies);
	}
	
	public void setNumOfCopies(int numOfCopies) {
        if (numOfCopies >= 0) {
            this.numOfCopies = numOfCopies;
        } else {
            this.numOfCopies = 0;
        }
    }
	
	public int getNumOfCopies() {
        return numOfCopies;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getISBN() {
        return ISBN;
    }
    
    // Increase copies
    public void addCopies(int count) {
        if (count > 0) {
            numOfCopies += count;
        }
    }

    // Decrease copies
    public boolean removeCopies(int count) {
        if (count <= numOfCopies) {
            numOfCopies -= count;
            return true;
        }
        return false;
    }
	
	public void display() {
		System.out.println("Book: " + bookTitle);
		System.out.println("Author: " + author);
		System.out.println("ISBN: " + ISBN);
		System.out.println("Quantity: " + numOfCopies);
	}
	
	
}
