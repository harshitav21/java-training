package question2;

public class Company implements Runnable{
	private Account account;
    public Company(Account account) {
        this.account = account;
    }
    
    
	
	@Override
	public void run() {
		for(int i=1; i<=100; i++) {
			account.addAmount(1000);
			System.out.println(Thread.currentThread().getName() + " deposited 1000");
		}
	}
}
