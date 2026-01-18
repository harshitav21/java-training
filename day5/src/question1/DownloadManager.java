package question1;

public class DownloadManager {
	public static void main(String[] args) {
		for (int i = 1; i <= 25; i++) {
            String fileName = "photo" + i + ".jpg";
            FileDownloader task = new FileDownloader(fileName);
            Thread worker = new Thread(task);

            worker.start();
        }
	}
}
