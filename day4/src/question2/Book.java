package question2;

import java.util.Objects;

public class Book {
	private String title;
	private String author;
	private double price;
	public Book(String title, String author, double price) {
		this.title = title;
		this.author = author;
		this.price = price;
	}
	
	public Book(String title, String author) { 
        this(title, author, 0.0);
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

	
	@Override
	public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Book)) return false;
        Book other = (Book) obj;
        return title.equalsIgnoreCase(other.title) && author.equalsIgnoreCase(other.author);
    }
	
	  @Override
	    public int hashCode() {
	        return Objects.hash(title.toLowerCase(), author.toLowerCase());
	    }
	  
	  @Override
	    public String toString() {
	        return title + " – " + author;
	    }
	
	
	
}
