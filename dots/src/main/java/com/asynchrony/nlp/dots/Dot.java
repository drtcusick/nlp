package com.asynchrony.nlp.dots;

public class Dot {
	
	private String subject;
	private String category;
	private String sentiment;
	
	public Dot(String subject, String category, String sentiment) {
		this.subject = subject;
		this.category = category;
		this.sentiment = sentiment;
	}
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSentiment() {
		return sentiment;
	}
	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}
	
	
	

}
