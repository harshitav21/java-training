package question4;

import java.io.*;
import java.util.LinkedList;

public class BookStore {
	private LinkedList<Book> books;
    public BookStore() {
        books = new LinkedList<>();
    }

    public void loadBooks(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                int id = Integer.parseInt(parts[0]);
                String isbn = parts[1];
                String title = parts[2];
                String author = parts[3];
                double price = Double.parseDouble(parts[4]);
                books.add(new Book(id, isbn, title, author, price));
            }
            System.out.println("Books loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public Book searchBook(int id) {
        for(Book b : books) {
            if(b.getId() == id)
                return b;
        }
        System.out.println("Book with ID " + id + " not found.");
        return null;
    }

    public void sellBook(String isbn, int noOfCopies) {
        for(Book b : books) {
            if(b.getIsbn().equals(isbn)) {
                try {
                    b.sell(noOfCopies);
                    System.out.println("Sold " + noOfCopies + " copies of " + b.getTitle() +
                                       " | New Price: " + String.format("%.2f", b.getPrice()));
                } catch (NotSufficientBookException e) {
                    System.out.println(e.getMessage());
                }
                return;
            }
        }
        System.out.println("Book with ISBN " + isbn + " not found.");
    }

    public void purchaseBook(String isbn, int noOfCopies) {
        for(Book b : books) {
            if(b.getIsbn().equals(isbn)) {
                b.purchase(noOfCopies);
                System.out.println("Purchased " + noOfCopies + " copies of " + b.getTitle());
                return;
            }
        }
        System.out.println("Book with ISBN " + isbn + " not found.");
    }

    public void displayBooks() {
        System.out.println("----- Book List -----");
        for(Book b : books) {
            System.out.println(b);
        }
    }
}
