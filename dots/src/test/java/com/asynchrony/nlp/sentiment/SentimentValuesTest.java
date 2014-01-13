package com.asynchrony.nlp.sentiment;

import static org.junit.Assert.*;

import org.junit.Test;

public class SentimentValuesTest {
	
	private SentimentValues testObject;

	@Test
	public void testSentimentValuesSentiment() {
		Sentiment sentiment = new Sentiment(CustomSentimentPipeline.SENTIMENT_VERY_POSITIVE,
				0.04, 0.1, 0.2, 0.6, 0.06);
		testObject = new SentimentValues(sentiment);
		assertEquals("Very positive", testObject.getSentimentStr());
		assertEquals(0.04f, testObject.getVeryNegative(), 0.001);
		assertEquals(0.1f, testObject.getNegative(), 0.001);
		assertEquals(0.2f, testObject.getNeutral(), 0.001);
		assertEquals(0.6f, testObject.getPositive(), 0.001);
		assertEquals(0.06f, testObject.getVeryPositive(), 0.001);
	}

	@Test
	public void testSentimentValuesStringDoubleDoubleDoubleDoubleDouble() {
		testObject = new SentimentValues(CustomSentimentPipeline.SENTIMENT_VERY_POSITIVE,
				0.04, 0.1, 0.2, 0.6, 0.06);
		assertEquals("Very positive", testObject.getSentimentStr());
		assertEquals(0.04f, testObject.getVeryNegative(), 0.001);
		assertEquals(0.1f, testObject.getNegative(), 0.001);
		assertEquals(0.2f, testObject.getNeutral(), 0.001);
		assertEquals(0.6f, testObject.getPositive(), 0.001);
		assertEquals(0.06f, testObject.getVeryPositive(), 0.001);
		Sentiment sentiment = testObject.getSentiment();
		assertEquals("Very positive", sentiment.getSentiment());
		String[] histogram = sentiment.getHistogram();
		assertEquals("0.040", histogram[0].trim());
		assertEquals("0.100", histogram[1].trim());
		assertEquals("0.200", histogram[2].trim());
		assertEquals("0.600", histogram[3].trim());
		assertEquals("0.060", histogram[4].trim());

	}

}
