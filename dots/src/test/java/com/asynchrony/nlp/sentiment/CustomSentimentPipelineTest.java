package com.asynchrony.nlp.sentiment;

import static org.junit.Assert.*;

import java.io.PrintStream;

import org.ejml.data.DenseMatrix64F;
import org.ejml.data.Matrix64F;
import org.ejml.simple.SimpleMatrix;
import org.junit.Before;
import org.junit.Test;

import com.asynchrony.nlp.classifier.TrainingSet;

import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.sentiment.Evaluate;
import edu.stanford.nlp.trees.Tree;

public class CustomSentimentPipelineTest {

	private static final boolean debugResults = true;
	private static final String TEST_SENTENCE_VERY_POS[] = { "The movie was very good." };
	private static final String TEST_SENTENCE_POS[] = {
		"Jason chimed in with the most brilliant solution we have heard.",
			"During the meeting, Steve was extremely insightful in his explaination of grey matter.",
			"An amazing thing occurred at stand-up this morning, everyone was on time.",
			"Steve is great", "Francis is great." };
	private static final String TEST_SENTENCE_NEUTRAL[] = {
			
			 };
	
	private static final String TEST_SENTENCE_NEG[] = {
		"Bob attended the meeting and was attentive.",
		"Mark spent personal time completing our proposal.",
		  "A fox jumped over a box while prancing through the woods.",
		  "Arun is a bad name for testing the software.",
		  "The new workstation had terrible graphics and my eyes hurt.",
			"I totally bombed my annual review and will get no cookies next year.",
			"Mark was terrible at solving the problem during the meeting.",
			"I don't know half of you half as well as I should like, and I like less than half of you half as well as you deserve",
			"The movie was too good" };
	private static final String TEST_SENTENCE_VERY_NEG[] = { "Dave interrupted the conversation and wasted everyone's time." };

	private static final String WRONG_SENTENCE_NEUTRAL[] = {
			};

	private static final String TEST_SENTENCE_NO_ASSERT[] = {
			"Arun is great",
			"I'm eagerly looking forward to Mr. Wiseau's future work.",
			"I don't know half of you half as well as I should like, and I like less than half of you half as well as you deserve" };

	private CustomSentimentPipeline testObject;
	private CustomSentimentMapper customSentimentMapper = new CustomSentimentMapper(0.1f);

	@Before
	public void setUp() {
		testObject = new CustomSentimentPipeline(customSentimentMapper);
	}
	
	@Test
	public void testExtracingMoreInfo() throws Exception {
		String sentence = "phenomenal fantasy best sellers";
		Sentiment sentiment = testObject.evaluateSentiment(sentence)[0];
		assertEquals("Positive", sentiment.getSentiment());
		String[] histogram = sentiment.getHistogram();
		assertHistogramSumsToOne(histogram);
	}
	

	@Test
	public void testSentencesWithNoAssert() throws Exception {
		for (int i = 0; i < TEST_SENTENCE_NO_ASSERT.length; i++) {
			Sentiment[] sentiments = testObject
					.evaluateSentiment(TEST_SENTENCE_NO_ASSERT[i]);
			if (sentiments.length == 1) {
				System.out.println(sentiments[0].getSentiment() + " : "
						+ TEST_SENTENCE_NO_ASSERT[i]);
			}
		}
	}
	
	@Test
	public void testGetSentimentFromSimpleMatrix() throws Exception {
		String sentence = "phenomenal fantasy best sellers";
		Sentiment sentiment = testObject.evaluateSentiment(sentence)[0];
		assertEquals("Positive", sentiment.getSentiment());
		String[] histogram = sentiment.getHistogram();
		assertEquals(5, histogram.length);
		assertHistogramSumsToOne(histogram);
	}

	@Test
	public void evaluateSentiment_Very_Positive() throws Exception {
		testParticularSentiment(CustomSentimentPipeline.SENTIMENT_VERY_POSITIVE, TEST_SENTENCE_VERY_POS);
	}

	@Test
	public void evaluateSentiment_Positive() throws Exception {
		testParticularSentiment(CustomSentimentPipeline.SENTIMENT_POSITIVE, TEST_SENTENCE_POS);
	}
	
	@Test
	public void evaluateSentiment_Neutral() throws Exception {
		testParticularSentiment(CustomSentimentPipeline.SENTIMENT_NEUTRAL, TEST_SENTENCE_NEUTRAL);
	}

	@Test
	public void evaluateSentiment_Negative() throws Exception {
		testParticularSentiment(CustomSentimentPipeline.SENTIMENT_NEGATIVE, TEST_SENTENCE_NEG);
	}

	@Test
	public void evaluateSentiment_Very_Negative() throws Exception {
		testParticularSentiment(CustomSentimentPipeline.SENTIMENT_VERY_NEGATIVE, TEST_SENTENCE_VERY_NEG);
	}

	@Test
	public void evaluateSentiment_Wrongfully_Neutral() throws Exception {
		testParticularSentiment(CustomSentimentPipeline.SENTIMENT_NEUTRAL, WRONG_SENTENCE_NEUTRAL);
	}
	
//	@Test
//	public void testAllTrainingSet() throws Exception {
//		testObject = new CustomSentimentPipeline(null);
//		String[][] trainingSetPhrases = TrainingSet.TRAINING_SET_PHRASES;
//		StringBuilder b = new StringBuilder();
//		for (String[] sentence : trainingSetPhrases) {
//			Sentiment[] sentiment = testObject.evaluateSentiment(sentence[1]);
//			b.append(sentiment[0].getSentiment() + "|" + sentence[1] + "\n");
//		}
//		System.out.println(b.toString());
//	}
	
	private void testParticularSentiment(String sentiment, String[] testCase)
			throws Exception {
		for (int i = 0; i < testCase.length; i++) {
			Sentiment[] sentiments = testObject.evaluateSentiment(testCase[i]);
			assertEquals(1, sentiments.length);
			assertEquals("Test Case [" + i + "] = " + testCase[i], sentiment,
					sentiments[0].getSentiment());
					if (debugResults)
					{
						System.out.println(sentiments[0].getSentiment() + histString(sentiments[0].getHistogram()) + " " + testCase[i]);
					}
		}
	}
	
	private void assertHistogramSumsToOne(String[] histogram) {
		double total = 0f;
		for (String value : histogram) {
			try
			{
				total += Double.valueOf(value).doubleValue();
			}
			catch (NumberFormatException e)
			{
				fail("The histogram included a non-number = " + value);
			}
		}
		assertEquals(1f, total, 0.01);
	}

	private String histString(String[] histogram) {
		StringBuilder b = new StringBuilder();
		for (String val : histogram) {
			b.append(val).append(" ");
		}
		return b.toString();
	}

}
