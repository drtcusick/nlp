package com.asynchrony.nlp.sentiment;

import org.junit.Before;
import org.junit.Test;

public class NeutralSentimentTest {

	
	private CustomSentimentPipeline testObject;
	private CustomSentimentMapper customSentimentMapper = new CustomSentimentMapper(0.03f);

	@Before
	public void setUp() {
//		testObject = new CustomSentimentPipeline(customSentimentMapper);
	}
	
	@Test
	public void testNeutralSentences() throws Exception {
		testObject = new CustomSentimentPipeline(null);
		String neutralSent[] = {
				"Ryan was thinking strategically when he took the initiative to learn a new technology.",
				"Thinking strategically, Ryan learned a new technology.",
				"David was thinking outside of the box when he solved the issue"
				 };
		StringBuilder b = new StringBuilder();
		for (String sentence : neutralSent) {
			Sentiment[] sentiment = testObject.evaluateSentiment(sentence);
			b.append(sentiment[0].getSentiment()).append("|");
			b.append(sentiment[0].getHistogramString()).append("|");
			b.append(sentence).append("\n");
		}
		System.out.println(b.toString());
	}

}
