package com.asynchrony.nlp.sentiment;

public class CustomSentimentMapper {

	public Sentiment adjustRawSentiment(Sentiment rawSentiment) {
		if (!CustomSentimentPipeline.SENTIMENT_NEUTRAL.equals(rawSentiment.getSentiment()))
		{
			return rawSentiment;
		}
		SentimentValues sentVals = new SentimentValues(rawSentiment);
		
		return rawSentiment;
	}

}
