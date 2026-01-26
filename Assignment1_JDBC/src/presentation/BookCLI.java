package presentation;

import java.util.Scanner;

import model.Book;
import service.BookService;

public class BookCLI {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			BookService service = new BookService();

			while (true) {
				System.out.println("\n---MENU---");
				System.out.println("1. Add");
				System.out.println("2. Find");
				System.out.println("3. List");
				System.out.println("4. Update");
				System.out.println("5. Delete");
				System.out.println("6. Exit");
				System.out.print("Enter your choice: ");
				int choice = sc.nextInt();

				try {
					switch (choice) {
					case 1 -> {
						System.out.print("ISBN: ");
						String isbn = sc.next();
						sc.nextLine(); 
						System.out.print("Title: ");
						String title = sc.nextLine();
						System.out.print("Author: ");
						String author = sc.nextLine();
						System.out.print("Price: ");
						double price = sc.nextDouble();

						service.addBook(new Book(isbn, title, author, price));
						System.out.println("Book added successfully!");
					}

					case 2 -> {
						System.out.print("ID: ");
						int id = sc.nextInt();
						Book book = service.findBook(id);
						if (book != null) {
							System.out.println(book);
						} else {
							System.out.println("Book not found with ID " + id);
						}
					}

					case 3 -> {
						for (Book b : service.listBooks()) {
							System.out.println(b.getId() + " | " + b.getTitle());
						}
					}

					case 4 -> {
						System.out.print("ID of the book to update: ");
						int id = sc.nextInt();
						sc.nextLine();
						System.out.print("New ISBN: ");
						String isbn = sc.nextLine();
						System.out.print("New Title: ");
						String title = sc.nextLine();
						System.out.print("New Author: ");
						String author = sc.nextLine();
						System.out.print("New Price: ");
						double price = sc.nextDouble();

						service.updateBook(id, new Book(isbn, title, author, price));
						System.out.println("Book updated successfully!");
					}

					case 5 -> {
						System.out.print("ID of the book to delete: ");
						int id = sc.nextInt();
						service.deleteBook(id);
						System.out.println("Book deleted successfully!");
					}

					case 6 -> System.exit(0);

					default -> System.out.println("Invalid choice, try again!");
					}
				} catch (Exception e) {
					System.out.println("Error: " + e.getMessage());
				}
			}
		}
	}
}
