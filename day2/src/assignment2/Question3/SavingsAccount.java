package assignment2.Question3;


public class SavingsAccount extends Account{
	private final double interest = 5.0;
	private final double minimumBalance = 5000;

    public SavingsAccount(String name, String accountNumber, double accountBalance) {
        super(name, accountNumber, accountBalance);
    }
    
    public double getBalance() {
        return accountBalance + (accountBalance * interest / 100);
    }

    @Override
    public void withdraw(double amount) {
    	 double maxWithdrawLimit = accountBalance;

         if (amount <= 0) {
             System.out.println("Invalid withdrawal amount.");
         } 
         else if (amount > maxWithdrawLimit) {
             System.out.println("Withdrawal amount exceeds maximum limit.");
         } 
         else if ((accountBalance - amount) < minimumBalance) {
             System.out.println("Withdrawal denied. Minimum balance of ₹5000 must be maintained.");
         } 
         else {
             accountBalance -= amount;
             System.out.println("Withdrawal successful.");
         }
    }
}
