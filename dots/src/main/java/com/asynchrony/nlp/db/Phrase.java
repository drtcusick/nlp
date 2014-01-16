package com.asynchrony.nlp.db;

public class Phrase {
	
	private long id;
	private String sentence;
	private int categoryId;
	private double sentiment;
	public Phrase(long id, String sentence, int categoryId, double sentiment) {
		this.id = id;
		this.sentence = sentence;
		this.categoryId = categoryId;
		this.sentiment = sentiment;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSentence() {
		return sentence;
	}
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategory(int categoryId) {
		this.categoryId = categoryId;
	}
	public double getSentiment() {
		return sentiment;
	}
	public void setSentiment(double sentiment) {
		this.sentiment = sentiment;
	}
	public String asString() {
		StringBuilder b = new StringBuilder();
		b.append(id).append(" ").append(categoryId).append(" ");
		b.append(sentiment).append(" ").append(sentence);
		return b.toString();
	}
	
}
