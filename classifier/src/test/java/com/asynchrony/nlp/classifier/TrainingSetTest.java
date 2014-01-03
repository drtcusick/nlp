package com.asynchrony.nlp.classifier;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class TrainingSetTest {
	
	private HashMap<String, String> props;
	private ColumnDataClassifierWrap classifierWrap;
	private static final String PATH = "C:\\workspace\\nlp\\classifier\\document\\examples\\";

	@Before
	public void setUp()
	{
		props = new HashMap<String, String>();
	}
	
	@Test
	public void testClassifySentence_allFromTrainingSet() throws Exception {
		props.put("-prop", PATH + "phrases.prop");
		StringBuilder results = new StringBuilder();
		int count = 0;
		for (int i = 0; i < TrainingSet.TRAINING_SET_PHRASES.length; i++) {
			results.append("\n").append(testSingleSentence(i, TrainingSet.TRAINING_SET_PHRASES));
		}
		
		System.out.println("TWC TWC TWC TWC TWC TWC TWC TWC TWC ");
		System.out.println("\n");
		System.out.println(results.toString());
		System.out.println("\n");
		System.out.println("TWC TWC TWC TWC TWC TWC TWC TWC TWC ");
	}

	private String testSingleSentence(int testNum, String[][] trainingSetPhrases) {
		String[] testLine = null;
		String[][] trainSet = new String[trainingSetPhrases.length - 1][2];
		int count = 0;
		for (int i = 0; i < trainingSetPhrases.length; i++) {
			if (i == testNum)
			{
				testLine = trainingSetPhrases[i];
			}
			else
			{
				trainSet[count++] = trainingSetPhrases[i];
			}
		}
		return testThisSentence(testLine, trainSet);
	}

	private String testThisSentence(String[] testLine, String[][] train) {
		classifierWrap = new ColumnDataClassifierWrap(props, train);
		CategoryResult classify = classifierWrap.classifySentence(testLine[1]);
		return classify.getCategoryText() + "  " + testLine[0] + "  " + classify.getProbability() + "  " + testLine[1];
	}


}
