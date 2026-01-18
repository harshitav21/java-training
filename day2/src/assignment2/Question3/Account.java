package assignment2.Question3;


public class Account {
	protected String name;
	protected String accountNumber;
    protected double accountBalance;

    public Account(String name, String accountNumber, double accountBalance) {
    	this.name = name;
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
        	accountBalance += amount;
            System.out.println("Amount deposited successfully");
        }else {
        	System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount <= accountBalance) {
            accountBalance -= amount;
            System.out.println("Withdrawn: " + amount);
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public String getName() {
        return name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
