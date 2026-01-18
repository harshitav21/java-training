package question4;

import java.util.Scanner;

public class BookApp {
 public static void main(String[] args) {
        BookStore store = new BookStore();
        store.loadBooks("books.txt");  

        Scanner sc = new Scanner(System.in);

        while(true) {
            System.out.println("\n1. Display Books");
            System.out.println("2. Search Book by ID");
            System.out.println("3. Sell Book");
            System.out.println("4. Purchase Book");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch(choice) {
                case 1:
                    store.displayBooks();
                    break;
                case 2:
                    System.out.print("Enter book ID: ");
                    int id = sc.nextInt();
                    Book b = store.searchBook(id);
                    if(b != null) System.out.println(b);
                    break;
                case 3:
                    System.out.print("Enter ISBN to sell: ");
                    String isbnSell = sc.nextLine();
                    System.out.print("Enter number of copies: ");
                    int sellCount = sc.nextInt();
                    store.sellBook(isbnSell, sellCount);
                    break;
                case 4:
                    System.out.print("Enter ISBN to purchase: ");
                    String isbnPurchase = sc.nextLine();
                    System.out.print("Enter number of copies: ");
                    int purchaseCount = sc.nextInt();
                    store.purchaseBook(isbnPurchase, purchaseCount);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    sc.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
