package assignment2.Question2;

import java.util.Scanner;

public class BookStore {
	private Book[] books = new Book[10];
    private int bookCount = 0;

    public void sell(String title, int count) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getBookTitle().equalsIgnoreCase(title)) {
                if (books[i].removeCopies(count)) {
                    System.out.println("Sold " + count + " copies of " + title);
                } else {
                    System.out.println("Not enough stock.");
                }
                return;
            }
        }
        System.out.println("Book not found.");
    }
    
    public void order(String isbn, int count) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getISBN().equals(isbn)) {
                books[i].addCopies(count);
                System.out.println("Stock updated for ISBN: " + isbn);
                return;
            }
        }

        if (bookCount >= books.length) {
            System.out.println("Bookstore is full. Cannot add new book.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Book Title: ");
        String title = sc.nextLine();
        System.out.print("Enter Author: ");
        String author = sc.nextLine();

        books[bookCount++] = new Book(title, author, isbn, count);
        System.out.println("New book added to store.");
        
    }

    public void display() {
        System.out.println("\n--- Book Inventory ---");
        for (int i = 0; i < bookCount; i++) {
            books[i].display();
        }
    }
}
