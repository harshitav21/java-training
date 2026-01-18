package assignment2.Question3;


public class CurrentAccount extends Account {
	private String tradeLicenseNumber;
	private double overdraftLimit = 5000;

	    public CurrentAccount(String name, String accountNumber, double accountBalance, String tradeLicenseNumber) {
	        super(name, accountNumber, accountBalance);
	        this.setTradeLicenseNumber(tradeLicenseNumber);
	    }
	    
	    public double getBalance(){
	    	return accountBalance;
	    }

	    @Override
	    public void withdraw(double amount) {
	    	 if (amount <= 0) {
	             System.out.println("Invalid withdrawal amount.");
	         } 
	        if (accountBalance + overdraftLimit >= amount) {
	            accountBalance -= amount;
	            System.out.println("Withdrawal successful: " + amount);
	        } else {
	            System.out.println("Withdrawal exceeds overdraft limit.");
	        }
	    }

		public String getTradeLicenseNumber() {
			return tradeLicenseNumber;
		}

		public void setTradeLicenseNumber(String tradeLicenseNumber) {
			this.tradeLicenseNumber = tradeLicenseNumber;
		}
}
