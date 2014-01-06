package com.asynchrony.nlp.parser;

import java.util.ArrayList;
import java.util.List;

public class SentenceSplitter {
	
	private static final String[] WORD_TRAILING_PUNCTUATIONS = 
										{".", ",", ";", ":"};
	
	public static String[] sentenceAsList(String sentence)
	{
		List<String> list = new ArrayList<String>();
		String[] split = sentence.split(" ");
		for (String word : split) {
			addWordToList(list, word);
		}
		return list.toArray(new String[list.size()]);
	}

	private static void addWordToList(List<String> list, String wordIn) {
		String word = wordIn;
		for (String punc : WORD_TRAILING_PUNCTUATIONS) {
			if (word.endsWith(punc))
			{
				list.add(word.replace(punc, ""));
				list.add(punc);
				return;
			}			
		}
		list.add(word);
	}

}
