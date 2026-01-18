package question1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WordFrequencyCounter{
	public static void main(String[] args) {
		Map<String, Integer> wordFreq = new HashMap<>();
		try(BufferedReader br = new BufferedReader(new FileReader("story.txt"))){
			String line;
			while((line = br.readLine()) != null) {
				
				line = line.trim().toLowerCase(); 
				if(line.isEmpty()) continue;
				line = line.replaceAll("[^a-z0-9\\s]", "");  //removes punctuation
				
				String words[] = line.split("\\s+"); //splits words by whitespace
				for(String word : words) {
					wordFreq.put(word, wordFreq.getOrDefault(word, 0) + 1);
				}
			}
		}catch(IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
		
		for(Map.Entry<String, Integer> entry : wordFreq.entrySet()) {
			System.out.println(entry.getKey() + " appears " + entry.getValue() + " times.");
		}
	}
}
