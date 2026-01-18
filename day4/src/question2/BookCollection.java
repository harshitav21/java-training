package question2;

import java.util.Arrays;
import java.util.Comparator;

public class BookCollection {
	private String ownerName;
	private Book[] books;
	public BookCollection(String ownerName, Book[] books) {
		super();
		this.ownerName = ownerName;
		this.books = books;
	}
	
	public boolean hasBook(Book b) {
        for (Book book : books) {
            if (book.equals(b)) return true;
        }
        return false;
    }

    // Sort books by title, then author
    public void sort() {
        Arrays.sort(books, new Comparator<Book>() {
            @Override
            public int compare(Book b1, Book b2) {
                int titleCompare = b1.getTitle().compareToIgnoreCase(b2.getTitle());
                if (titleCompare != 0) return titleCompare;
                return b1.getAuthor().compareToIgnoreCase(b2.getAuthor());
            }
        });
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Owner: ").append(ownerName).append("\nBooks:\n");
        for (Book b : books) {
            sb.append(b.toString()).append("\n");
        }
        return sb.toString();
    }
}
