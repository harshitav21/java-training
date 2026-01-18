package question3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MaxDoubleFromFile {
	public static void main(String[] args) {
        String filename = "data.txt";
        List<Double> numbers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    double value = Double.parseDouble(line.trim());
                    numbers.add(value);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number skipped: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
            return;
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        if (numbers.isEmpty()) {
            System.out.println("No valid numbers found in the file.");
            return;
        }

        double max = Collections.max(numbers);
        System.out.println("Largest value: " + max);
    }
}
