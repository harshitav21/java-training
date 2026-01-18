package question2;

public class BookCollectionTest {
	public static void main(String[] args) {
        Book[] myBooks = {
            new Book("Java Basics", "Raj"),
            new Book("Java in Depth", "Mehta"),
            new Book("Spring Boot", "Sharma"),
            new Book("Java Basics", "Ravi") // to check sorting by author if title same
        };

        BookCollection collection = new BookCollection("Rajeev Gupta", myBooks);

        // Check if a specific book exists
        Book searchBook = new Book("Java in Depth", "Mehta");
        if (collection.hasBook(searchBook)) {
            System.out.println(searchBook.getTitle() + " exists in the collection.");
        } else {
            System.out.println(searchBook.getTitle() + " does NOT exist in the collection.");
        }

        // Sort and print the collection
        collection.sort();
        System.out.println(collection);
    }
}
