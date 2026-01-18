package question2;
import java.io.*;

public class FileCopyPerformanceTest {

	public static void main(String[] args) {
	
	    String sourceFile = "source.png";
	
	    long nonBufferedTime = copyWithoutBuffer(sourceFile, "copy_nonbuffered.png");
	    long bufferedTime = copyWithBuffer(sourceFile, "copy_buffered.png");
	
	    System.out.println("Without Buffering: " + nonBufferedTime + " ms");
	    System.out.println("With Buffering: " + bufferedTime + " ms");
	    System.out.println("Performance improved by: " + (nonBufferedTime - bufferedTime) + " ms");
	}
	
	public static long copyWithoutBuffer(String source, String destination) {
	
	    long startTime = System.currentTimeMillis();
	
	    try {
	        FileInputStream fis = new FileInputStream(source);
	        FileOutputStream fos = new FileOutputStream(destination);
	
	        int data;
	        while ((data = fis.read()) != -1) {
	            fos.write(data);
	        }
	
	        fis.close();
	        fos.close();
	
	    } catch (IOException e) {
	        System.out.println("Error (Non-buffered): " + e.getMessage());
	    }
	
	    return System.currentTimeMillis() - startTime;
	}
	
	public static long copyWithBuffer(String source, String destination) {
	
	    long startTime = System.currentTimeMillis();
	
	    try {
	        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source));
	        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destination));
	
	        int data;
	        while ((data = bis.read()) != -1) {
	            bos.write(data);
	        }
	
	        bis.close();
	        bos.close();
	
	    } catch (IOException e) {
	        System.out.println("Error (Buffered): " + e.getMessage());
	    }
	
	    return System.currentTimeMillis() - startTime;
	}
}
