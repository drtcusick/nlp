package com.asynchrony.nlp.sentiment;

public class CustomSentimentMapper {
	
	private static final double DEFAULT_SKEW_TRESHOLD = 0.03f;
	private static final double REDUCE_NEUTRAL_WEIGHTS[] = {-2f, -1f, 0f, 1f, 2f};
	
	private double skewThreshold = DEFAULT_SKEW_TRESHOLD;
	
	
	public CustomSentimentMapper()
	{
		this.skewThreshold = DEFAULT_SKEW_TRESHOLD;
	}

	public CustomSentimentMapper(double skewThreshold)
	{
		this.skewThreshold  = skewThreshold;
	}
	
	public Sentiment adjustRawSentiment(Sentiment rawSentiment) {
		if (!CustomSentimentPipeline.SENTIMENT_NEUTRAL.equals(rawSentiment.getSentiment()))
		{
			return rawSentiment;
		}
		SentimentValues sentVals = new SentimentValues(rawSentiment);
		return reduceNeutrals(sentVals);
	}

	private Sentiment reduceNeutrals(SentimentValues sentVals) {
		double total = getWeightedTotal(sentVals);
		if (total < -skewThreshold)
		{
			return changeSentiment(CustomSentimentPipeline.SENTIMENT_NEGATIVE, sentVals.getSentiment());
		}
		else if (total > skewThreshold)
		{
			return changeSentiment(CustomSentimentPipeline.SENTIMENT_POSITIVE, sentVals.getSentiment());
		}
		return sentVals.getSentiment();
	}

	private Sentiment changeSentiment(String newSentimentStr,
			Sentiment oldSentiment) {
		return new Sentiment(newSentimentStr, oldSentiment.getHistogram());
	}

	private double getWeightedTotal(SentimentValues sentVals) {
		double total = 0f;
		total += (sentVals.getVeryNegative() * REDUCE_NEUTRAL_WEIGHTS[0]);
		total += (sentVals.getNegative() * REDUCE_NEUTRAL_WEIGHTS[1]);
		total += (sentVals.getNeutral() * REDUCE_NEUTRAL_WEIGHTS[2]);
		total += (sentVals.getPositive() * REDUCE_NEUTRAL_WEIGHTS[3]);
		total += (sentVals.getVeryPositive() * REDUCE_NEUTRAL_WEIGHTS[4]);
		return total;
	}

}
