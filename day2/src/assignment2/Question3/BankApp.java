package assignment2.Question3;


public class BankApp {

    public static void main(String[] args) {

        System.out.println("----- SAVINGS ACCOUNT DEMO -----");

        SavingsAccount savings = new SavingsAccount(
                "Amit",
                "SA101",
                10000
        );

        savings.deposit(2000);
        savings.withdraw(3000);

        System.out.println("Savings Account Balance (without interest): ₹" + savings.getAccountBalance());

        System.out.println("Savings Account Balance (with interest): ₹" + savings.getBalance());

        System.out.println("\n----- CURRENT ACCOUNT DEMO -----");

        CurrentAccount current = new CurrentAccount(
                "Rohit",
                "CA201",
                5000,
                "TL-9988"
        );

        current.deposit(3000);
        current.withdraw(9000);   // Uses overdraft
        current.withdraw(5000);   

        System.out.println("Current Account Balance: ₹" + current.getBalance());
    }
}

