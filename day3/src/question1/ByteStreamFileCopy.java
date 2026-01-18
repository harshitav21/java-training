package question1;

import java.io.*;

public class ByteStreamFileCopy {
	public static void main(String[] args) {

        String sourceFile = "source.png";  
        String destFile = "copy_byte.png";

        try {
            FileInputStream fis = new FileInputStream(sourceFile);
            FileOutputStream fos = new FileOutputStream(destFile);

            int b;
            while ((b = fis.read()) != -1) {  
                fos.write(b);                
            }

            fis.close();
            fos.close();

            File original = new File(sourceFile);
            File copy = new File(destFile);

            System.out.println("Byte Stream Copy Completed!");
            System.out.println("Original size: " + original.length() + " bytes");
            System.out.println("Copied size:   " + copy.length() + " bytes");

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
        }
    }
}
