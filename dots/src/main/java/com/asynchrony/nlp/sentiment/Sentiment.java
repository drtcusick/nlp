package com.asynchrony.nlp.sentiment;

public class Sentiment {

	private String histogram[] = null;
	private String sentiment = null;
	
	public Sentiment(String sentiment, String[] histogram) {
		this.sentiment = sentiment;
		this.histogram = histogram;
	}
	public String[] getHistogram() {
		return histogram;
	}
	public void setHistogram(String[] histogram) {
		this.histogram = histogram;
	}
	public String getSentiment() {
		return sentiment;
	}
	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}
	
	
}
