package presentation;

import java.util.Scanner;

import model.Book;
import service.BookService;

public class BookMain {
	public static void main(String[] args) {

		BookService service = new BookService();
		try (Scanner sc = new Scanner(System.in)) {
			while (true) {
				System.out.println("\n--- MENU ---");
				System.out.println("1. Add");
				System.out.println("2. Find");
				System.out.println("3. List");
				System.out.println("4. Update");
				System.out.println("5. Delete");
				System.out.println("6. Exit");
				System.out.print("Choice: ");

				int choice = sc.nextInt();
				sc.nextLine();

				try {
					switch (choice) {
					case 1: {
						System.out.print("ISBN: ");
						String isbn = sc.nextLine();
						System.out.print("Title: ");
						String title = sc.nextLine();
						System.out.print("Author: ");
						String author = sc.nextLine();
						System.out.print("Price: ");
						double price = sc.nextDouble();

						service.addBook(new Book(isbn, title, author, price));
						System.out.println("Book added!");
						break;
					}

					case 2: {
						System.out.print("ID: ");
						int id = sc.nextInt();
						System.out.println(service.getBookById(id));
						break;
					}

					case 3:
						service.getAllBooks().forEach(System.out::println);
						break;

					case 4: {
						System.out.print("ID: ");
						int id = sc.nextInt();
						sc.nextLine();
						System.out.print("ISBN: ");
						String isbn = sc.nextLine();
						System.out.print("Title: ");
						String title = sc.nextLine();
						System.out.print("Author: ");
						String author = sc.nextLine();
						System.out.print("Price: ");
						double price = sc.nextDouble();

						service.updateBook(id, new Book(isbn, title, author, price));
						System.out.println("Updated!");
						break;
					}

					case 5: {
						System.out.print("ID: ");
						int id = sc.nextInt();
						service.deleteBook(id);
						System.out.println("Deleted!");
						break;
					}

					case 6:
						System.exit(0);
					default:
						System.out.println("Invalid choice!");
					}
				} catch (Exception e) {
					System.out.println("Error: " + e.getMessage());
				}
			}
		}
	}
}
