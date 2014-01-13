package com.asynchrony.nlp.sentiment;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CustomSentimentMapperTest {
	
	private CustomSentimentMapper testObject;

	@Before
	public void setUp()
	{
		testObject = new CustomSentimentMapper();
	}
	
	@Test
	public void testAdjustSentiment_NeutralToNegative() {
		Sentiment rawSentiment = createTestSentiment(CustomSentimentPipeline.SENTIMENT_NEUTRAL, 
				"0.067", "0.332", "0.344", "0.207", "0.049");
		Sentiment adjustedSentiment = testObject.adjustRawSentiment(rawSentiment);
		assertEquals(CustomSentimentPipeline.SENTIMENT_NEGATIVE, adjustedSentiment.getSentiment());
	}

	@Test
	public void testAdjustSentiment_NeutralToPositive() {
		Sentiment rawSentiment = createTestSentiment(CustomSentimentPipeline.SENTIMENT_NEUTRAL, 
				"0.049", "0.207", "0.344", "0.332", "0.067");
		Sentiment adjustedSentiment = testObject.adjustRawSentiment(rawSentiment);
		assertEquals(CustomSentimentPipeline.SENTIMENT_POSITIVE, adjustedSentiment.getSentiment());
	}
	
	private Sentiment createTestSentiment(String sentiment, String veryNeg,
			String neg, String neut, String pos, String veryPos) {
		String[] histogram = {veryNeg, neg, neut, pos, veryPos};
		return new Sentiment(sentiment, histogram);
	}

}
