package question2;

public class MainApp {
	public static void main(String[] args) throws InterruptedException {
		Account account = new Account(10_000);
		Bank bank = new Bank(account);
		Company company = new Company(account);
		Thread bankThread = new Thread(bank, "Bank-Thread");
		Thread companyThread = new Thread(company, "Company-Thread");
		
		bankThread.start();
		companyThread.start();
		
		bankThread.join();     // main thread waits until bankThread finishes
		companyThread.join(); // main thread waits until companyThread finishes
		
		System.out.println("Final Balance: Rs" + account.getBalance());
	}
}
