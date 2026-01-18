package question1;

import java.io.*;

public class CharStreamFileCopy {
	public static void main(String[] args) {

        String sourceFile = "source.txt";   
        String destFile = "copy_char.txt";

        try {
            FileReader fr = new FileReader(sourceFile);
            FileWriter fw = new FileWriter(destFile);

            int ch;
            while ((ch = fr.read()) != -1) {  
                fw.write(ch);                 
            }

            fr.close();
            fw.close();

            File original = new File(sourceFile);
            File copy = new File(destFile);

            System.out.println("Character Stream Copy Completed!");
            System.out.println("Original size: " + original.length() + " bytes");
            System.out.println("Copied size:   " + copy.length() + " bytes");

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
        }
    }
}
