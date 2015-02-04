package ir.assignments.three;

import java.io.File;

import java.util.List;

import ir.assignments.three.Frequency;
import ir.assignments.three.Utilities;
import ir.assignments.three.WordFrequencyCounter;

public class TestCommonWords {

	 public static void main(String[] args) {

	    	
	        File file = new File("allText.txt");
	        List<String> words = Utilities.tokenizeFile(file);
			WordFrequencyCounter.computeWordFrequencies(words);
			List<Frequency> frequencies = WordFrequencyCounter.computeWordFrequencies(words);
			Utilities.printFrequencies(frequencies);

	    }
}


