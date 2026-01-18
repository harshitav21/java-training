package question1;

public class FileDownloader implements Runnable{
	
	private final String fileName;
    public FileDownloader(String fileName) {
        this.fileName = fileName;
    }
    
    @Override
	public void run() {
    	System.out.println("Starting download: " + fileName);
        try {
            int downloadTime = 200 + (int) (Math.random() * 301);
            Thread.sleep(downloadTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Download interrupted: " + fileName);
            return;
        }

        System.out.println("Completed: " + fileName);
	}
}
