package com.asynchrony.nlp.sentiment;

public class SentimentValues {
	
	private String sentimentStr;
	private Sentiment sentiment;
	private double veryNegative;
	private double negative;
	private double neutral;
	private double positive;
	private double veryPositive;
	
	public SentimentValues(Sentiment sentiment) {
		this.sentiment = sentiment;
		this.sentimentStr = sentiment.getSentiment();
		computeNumericValues();
	}
	
	
	public SentimentValues(String sentimentStr, double veryNegative,
			double negative, double neutral, double positive,
			double veryPositive) {
		this.sentimentStr = sentimentStr;
		this.veryNegative = veryNegative;
		this.negative = negative;
		this.neutral = neutral;
		this.positive = positive;
		this.veryPositive = veryPositive;
		setSentimentFromCurrentValues(sentimentStr);
	}

	private void setSentimentFromCurrentValues(String sentimentStr) {
		String doubleFormat = CustomSentimentPipeline.HISTOGRAM_VALUE_FORMAT;
		String[] histogram = {String.format(doubleFormat, this.veryNegative),
				String.format(doubleFormat, this.negative),
				String.format(doubleFormat, this.neutral),
				String.format(doubleFormat, this.positive),
				String.format(doubleFormat, this.veryPositive)};
		this.sentiment = new Sentiment(sentimentStr, histogram);
	}

	private void computeNumericValues() {
		String[] histogram = sentiment.getHistogram();
		this.veryNegative = getDoubleValue(histogram[0]);
		this.negative = getDoubleValue(histogram[1]);
		this.neutral = getDoubleValue(histogram[2]);
		this.positive = getDoubleValue(histogram[3]);
		this.veryPositive = getDoubleValue(histogram[4]);
	}


	private double getDoubleValue(String strVal) {
		try
		{
			return Double.valueOf(strVal);
		}
		catch (NumberFormatException e)
		{
			return 0.0;
		}
	}


	public String getSentimentStr() {
		return sentimentStr;
	}


	public void setSentimentStr(String sentimentStr) {
		this.sentimentStr = sentimentStr;
	}


	public Sentiment getSentiment() {
		return sentiment;
	}


	public void setSentiment(Sentiment sentiment) {
		this.sentiment = sentiment;
	}


	public double getVeryNegative() {
		return veryNegative;
	}


	public void setVeryNegative(double veryNegative) {
		this.veryNegative = veryNegative;
	}


	public double getNegative() {
		return negative;
	}


	public void setNegative(double negative) {
		this.negative = negative;
	}


	public double getNeutral() {
		return neutral;
	}


	public void setNeutral(double neutral) {
		this.neutral = neutral;
	}


	public double getPositive() {
		return positive;
	}


	public void setPositive(double positive) {
		this.positive = positive;
	}


	public double getVeryPositive() {
		return veryPositive;
	}


	public void setVeryPositive(double veryPositive) {
		this.veryPositive = veryPositive;
	}
	
	
	
	

}
