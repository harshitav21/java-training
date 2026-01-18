package question2;

public class Account {

    private double balance;

    public Account(double initialBalance) {
        this.balance = initialBalance;
    }

    // Method-level synchronization
    public synchronized void addAmount(double amount) {

        if (amount <= 0) {
            System.out.println(Thread.currentThread().getName() + " : Invalid deposit amount!");
            return;
        }

        balance += amount;
    }

    // Block-level synchronization
    public void subtractAmount(double amount) {

        synchronized (this) {

            if (amount <= 0) {
                System.out.println(Thread.currentThread().getName() + "Invalid withdrawal amount!");
                return;
            }

            if (balance < amount) {
                System.out.println(Thread.currentThread().getName() + "Insufficient balance!");
                return;
            }

            balance -= amount;
        }
    }

    public synchronized double getBalance() {
        return balance;
    }
}
