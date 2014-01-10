package com.asynchrony.nlp.sentiment;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.stanford.nlp.sentiment.Evaluate;

public class CustomSentimentPipelineTest {

	private static final String SENTIMENT_VERY_POSITIVE = "Very positive";
	private static final String SENTIMENT_POSITIVE = "Positive";
	private static final String SENTIMENT_NEUTRAL = "Neutral";
	private static final String SENTIMENT_NEGATIVE = "Negative";
	private static final String SENTIMENT_VERY_NEGATIVE = "Very negative";

	private static final String TEST_SENTENCE_VERY_POS[] = { "The movie was very good." };
	private static final String TEST_SENTENCE_POS[] = {
			"During the meeting, Steve was extremely insightful in his explaination of grey matter.",
			"An amazing thing occurred at stand-up this morning, everyone was on time.",
			"Steve is great" };
	private static final String TEST_SENTENCE_NEUTRAL[] = {
			"A fox jumped over a box while prancing through the woods.",
			"Bob attended the meeting and was attentive.",
			"Mark spent personal time completing our proposal." };
	private static final String TEST_SENTENCE_NEG[] = {
			"The new workstation had terrible graphics and my eyes hurt.",
			"I totally bombed my annual review and will get no cookies next year.",
			"Mark was terrible at solving the problem during the meeting.",
			"I don't know half of you half as well as I should like, and I like less than half of you half as well as you deserve",
			"The movie was too good" };
	private static final String TEST_SENTENCE_VERY_NEG[] = { "Dave interrupted the conversation and wasted everyone's time." };

	private static final String WRONG_SENTENCE_NEG[] = {
			"Jason chimed in with the most brilliant solution we have heard.",
			"Francis is great." };

	private static final String WRONG_SENTENCE_NEUTRAL[] = {
			"Arun is a bad name for testing the software."};

	private static final String TEST_SENTENCE_NO_ASSERT[] = {
			"Arun is great",
			"I'm eagerly looking forward to Mr. Wiseau's future work.",
			"I don't know half of you half as well as I should like, and I like less than half of you half as well as you deserve" };

	private CustomSentimentPipeline testObject;

	@Before
	public void setUp() {
		testObject = new CustomSentimentPipeline();
	}
	
	@Test
	public void testExtracingMoreInfo() throws Exception {
		String sentence = "Steve is great.";
		String[] sentiments = testObject.evaluateSentiment(sentence, true);
		System.out.println("TWC sentiments.length = " + sentiments.length);
		int i = 0;
		for (String sentiment : sentiments) {
			System.out.println("TWC sentiment[" + i++ + "] = " + sentiment);
		}
	}

	@Test
	public void testSentencesWithNoAssert() throws Exception {
		for (int i = 0; i < TEST_SENTENCE_NO_ASSERT.length; i++) {
			String[] sentiments = testObject
					.evaluateSentiment(TEST_SENTENCE_NO_ASSERT[i]);
			if (sentiments.length == 1) {
				System.out.println(sentiments[0] + " : "
						+ TEST_SENTENCE_NO_ASSERT[i]);
			}
		}
	}

	@Test
	public void evaluateSentiment_Very_Positive() throws Exception {
		testParticularSentiment(SENTIMENT_VERY_POSITIVE, TEST_SENTENCE_VERY_POS);
	}

	@Test
	public void evaluateSentiment_Positive() throws Exception {
		testParticularSentiment(SENTIMENT_POSITIVE, TEST_SENTENCE_POS);
	}
	
	@Test
	public void evaluateSentiment_Neutral() throws Exception {
		testParticularSentiment(SENTIMENT_NEUTRAL, TEST_SENTENCE_NEUTRAL);
	}

	@Test
	public void evaluateSentiment_Negative() throws Exception {
		testParticularSentiment(SENTIMENT_NEGATIVE, TEST_SENTENCE_NEG);
	}

	@Test
	public void evaluateSentiment_Very_Negative() throws Exception {
		testParticularSentiment(SENTIMENT_VERY_NEGATIVE, TEST_SENTENCE_VERY_NEG);
	}

	@Test
	public void evaluateSentiment_Wrongfully_Negative() throws Exception {
		testParticularSentiment(SENTIMENT_NEGATIVE, WRONG_SENTENCE_NEG);
	}

	@Test
	public void evaluateSentiment_Wrongfully_Neutral() throws Exception {
		testParticularSentiment(SENTIMENT_NEUTRAL, WRONG_SENTENCE_NEUTRAL);
	}
	
	private void testParticularSentiment(String sentiment, String[] testCase)
			throws Exception {
		for (int i = 0; i < testCase.length; i++) {
			String[] sentiments = testObject.evaluateSentiment(testCase[i]);
			assertEquals(1, sentiments.length);
			assertEquals("Test Case [" + i + "] = " + testCase[i], sentiment,
					sentiments[0]);
		}
	}

}
