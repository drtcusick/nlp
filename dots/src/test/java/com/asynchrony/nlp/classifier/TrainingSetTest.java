package com.asynchrony.nlp.classifier;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class TrainingSetTest {
	
	private HashMap<String, String> props;
	private ColumnDataClassifierWrap classifierWrap;
	private static final String PATH = "C:\\workspace\\nlp\\dots\\document\\examples\\";

	@Before
	public void setUp()
	{
		props = new HashMap<String, String>();
	}
	
	@Test
	public void testClassifySentence_allFromTrainingSet() throws Exception {
		props.put("-prop", PATH + "phrases.prop");
		StringBuilder results = new StringBuilder();
		// Remove comment to test results for entire training set.  Takes some time...
//		for (int i = 0; i < TrainingSet.TRAINING_SET_PHRASES.length; i++) {
//			results.append("\n").append(testSingleSentence(i, TrainingSet.TRAINING_SET_PHRASES));
//		}
		results.append("            TESTING COMPLETE TRAINING SET TURNED OFF.          ");
		System.out.println("------------ ALL SENTENCES IN TRAINING SET RESULTS --------- ");
		System.out.println("\n");
		System.out.println(results.toString());
		System.out.println("\n");
		System.out.println("------------ ALL SENTENCES IN TRAINING SET RESULTS --------- ");
	}
	
	@Test
	public void testTWCdeleteThisWhenDoneMakingTrainingSet() throws Exception {
		int i = 0;
		StringBuilder b = new StringBuilder();
		StringBuilder c = new StringBuilder();
		for (String[] item : TrainingSet.TRAINING_SET_PHRASES) {
			c.append(i).append(",").append("13,13,13\n");
			b.append(i++).append(",").append(item[2]).append("\n");
		}
		System.out.println(b.toString());
		System.out.println("\n");
		System.out.println(c.toString());
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
