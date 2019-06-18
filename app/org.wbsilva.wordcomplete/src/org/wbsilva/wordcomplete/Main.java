package org.wbsilva.wordcomplete;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

	public static void main(String args[]) {
		System.out.println("[D]eutsch      [E]nglish");
		
		String wordsFile = null;
		
		try(final BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in))) {
			final String inputLang = inReader.readLine();
			if (inputLang.toLowerCase().equals("d")) {
				wordsFile = "resources/deutsch_leipzig.lst";
			} else if (inputLang.toLowerCase().equals("e")) {
				wordsFile = "resources/words_alpha.lst";
			}
		
			if (wordsFile != null) {
				final ArrayList<String> words = new ArrayList<>();
				
				try(final BufferedReader reader = new BufferedReader(new FileReader(wordsFile))) {	
					String word = reader.readLine();
					
					while (word != null) {
						if (word.length() > 2)
							words.add(word);
						
						word = reader.readLine();
					}
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				final String hint = chooseHint(words);
				
				boolean end = false;
				while(!end) {
					System.out.print(hint);
					
					final String inputWord = inReader.readLine();
						
					if (inputWord.length() > 0) {
						final boolean match = words.stream().anyMatch(w -> w.toLowerCase().equals((hint+inputWord).toLowerCase()));
						
						if (match) {
							System.out.println("Good");
						} else {
							System.out.println("XXXX");
						}
						end = false;
					} else {
						end = true;
					}
				}
				
				final List<String> exampleWords = words.stream()
						.filter(w -> w.toLowerCase().substring(0, 2).equals(hint.toLowerCase()))
						.distinct()
						.collect(Collectors.toList());
				
				Collections.shuffle(exampleWords);
				
				System.out.println("..."+exampleWords.stream()
					.limit(10)
					.map(w -> ", " + w)
					.reduce("", String::concat));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String chooseHint(final List<String> words) {
		final int pos = new Random().nextInt(words.size());
		
		final String word = words.get(pos);
		
		return word.substring(0, 2);
	}
}
