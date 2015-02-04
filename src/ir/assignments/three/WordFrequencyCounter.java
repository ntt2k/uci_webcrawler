package ir.assignments.three;

import ir.assignments.three.Frequency;
import ir.assignments.three.Utilities;

import java.io.File;
import java.util.*;

// add-on library


/**
 * Counts the total number of words and their frequencies in a text file.
 */
public final class WordFrequencyCounter {
	/**
	 * This class should not be instantiated.
	 */
	private WordFrequencyCounter() {}


	/**
	 * Takes the input list of words and processes it, returning a list
	 * of {@link Frequency}s.
	 * 
	 * This method expects a list of lowercase alphanumeric strings.
	 * If the input list is null, an empty list is returned.
	 * 
	 * There is one frequency in the output list for every 
	 * unique word in the original list. The frequency of each word
	 * is equal to the number of times that word occurs in the original list. 
	 * 
	 * The returned list is ordered by decreasing frequency, with tied words sorted
	 * alphabetically.
	 * 
	 * The original list is not modified.
	 * 
	 * Example:
	 * 
	 * Given the input list of strings 
	 * ["this", "sentence", "repeats", "the", "word", "sentence"]
	 *
	 * The output list of frequencies should be
	 * ["sentence:2", "the:1", "this:1", "repeats:1",  "word:1"]
	 *  
	 * @param words A list of words.
	 * @return A list of word frequencies, ordered by decreasing frequency.
	 */
	public static List<Frequency> computeWordFrequencies(List<String> words) {

		List<Frequency> result = new ArrayList<Frequency>();

		Set<String> uniqueSet = new HashSet<String>(words);

		
		for (String item : uniqueSet)
			result.add(new Frequency(item, Collections.frequency(words, item)));


		Collections.sort(result, new Comparator<Frequency>(){

			@Override
			public int compare(Frequency d1, Frequency d2){

				// compare by frequency
				int temp1 = Integer.compare(d2.getFrequency(), d1.getFrequency());
				if (temp1 != 0)
					return temp1;

				// compare by alphabetically order
				return d1.getText().compareTo(d2.getText());
			}
		});

//		System.out.println("Debug msg 2 -> " + result);
		return result;
	}
	
	/**
	 * Runs the word frequency counter. The input should be the path to a text file.
	 * 
	 * @param args The first element should contain the path to a text file.
	 */
	public static void main(String[] args) {
		File file = new File(args[0]);
		List<String> words = Utilities.tokenizeFile(file);
		List<Frequency> frequencies = computeWordFrequencies(words);
		Utilities.printFrequencies(frequencies);
	}
}
