package ir.assignments.three;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;


/**
 * A collection of utility methods for text processing.
 */
public class Utilities {
	/**
	 * Reads the input text file and splits it into alphanumeric tokens.
	 * Returns an ArrayList of these tokens, ordered according to their
	 * occurrence in the original text file.
	 * 
	 * Non-alphanumeric characters delineate tokens, and are discarded.
	 *
	 * Words are also normalized to lower case. 
	 * 
	 * Example:772552695613
	 * 
	 * Given this input string
	 * "An input string, this is! (or is it?)"
	 * 
	 * The output list of strings should be
	 * ["an", "input", "string", "this", "is", "or", "is", "it"]
	 * 
	 * @param input The file to read in and tokenize.
	 * @return The list of tokens (words) from the input file, ordered by occurrence.
	 */
	public static ArrayList<String> tokenizeFile(File input) {
		// TODO Write body!
		Scanner sc = null;

		try {
			sc = new Scanner(input);
		}
		catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error accessing the source file: \"" + input + "\"");
			System.exit(-2);
		}

		ArrayList<String> result = new ArrayList<String>();


		while (sc.hasNext()) {
			result.add(sc.next().replaceAll("\\W+","").toLowerCase());
		}

//		System.out.println("Debug msg 1 -> " + result);

		return result;
	}
	
	/**
	 * Takes a list of {@link Frequency}s and prints it to standard out. It also
	 * prints out the total number of items, and the total number of unique items.
	 * 
	 * Example one:
	 * 
	 * Given the input list of word frequencies
	 * ["sentence:2", "the:1", "this:1", "repeats:1",  "word:1"]
	 * 
	 * The following should be printed to standard out
	 * 
	 * Total item count: 6
	 * Unique item count: 5
	 * 
	 * sentence	2
	 * the		1
	 * this		1
	 * repeats	1
	 * word		1
	 * 
	 * 
	 * Example two:
	 * 
	 * Given the input list of 2-gram frequencies
	 * ["you think:2", "how you:1", "know how:1", "think you:1", "you know:1"]
	 * 
	 * The following should be printed to standard out
	 * 
	 * Total 2-gram count: 6
	 * Unique 2-gram count: 5
	 * 
	 * you think	2
	 * how you		1
	 * know how		1
	 * think you	1
	 * you know		1
	 * 
	 * @param frequencies A list of frequencies.
	 */
	public static void printFrequencies(List<Frequency> frequencies) {
		// TODO Write body!
		int total_count = 0;

		for (Frequency item : frequencies) {
			total_count += item.getFrequency();
		}

		// Print out:
		if (total_count > 0) {

			System.out.println("-----------------------------------");
			if (frequencies.get(0).getText().split(" ").length >= 2) {
				System.out.println("Total 2-gram count: " + total_count);
				System.out.println("Unique 2-gram count: " + frequencies.size());
			}
			else {
				System.out.println("Total item count: " + total_count);
				System.out.println("Unique item count: " + frequencies.size());
			}
		}

		System.out.println();

		for (Frequency item: frequencies) {
			System.out.printf("%-17s %d %n", item.getText(), item.getFrequency());
		}
	}
}
