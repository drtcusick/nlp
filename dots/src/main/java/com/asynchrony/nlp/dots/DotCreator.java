package com.asynchrony.nlp.dots;

import com.asynchrony.nlp.sentiment.CustomSentimentMapper;

public class DotCreator {
	
	public static final String DOT_PART_SUBJECT = "dotPartSubject";
	public static final String DOT_PART_CATEGORY = "dotPartCategory";
	public static final String DOT_PART_SENTIMENT = "dotPartSentiment";
	
	private String sentence = null;
	private String subject = null;
	private String category = null;
	private String sentiment = null;
	private boolean withProbability = false;
	private IDotCreatorListener listener;
	private CustomSentimentMapper sentimentMapper = null;
	
	public DotCreator(IDotCreatorListener listener, CustomSentimentMapper sentimentMapper, 
			String sentence, boolean withProbability)
	{
		this.listener = listener;
		this.sentimentMapper  = sentimentMapper;
		this.sentence = sentence;
		this.withProbability = withProbability;
	}
	
	public void launchDotCreation()
	{
		DotPartThread threadSubject = new DotPartThread(this, null, sentence, DOT_PART_SUBJECT, withProbability);
		threadSubject.run();
		DotPartThread threadCategory = new DotPartThread(this, null, sentence, DOT_PART_CATEGORY, withProbability);
		threadCategory.run();
		DotPartThread threadSentiment = new DotPartThread(this, sentimentMapper, sentence, DOT_PART_SENTIMENT, withProbability);
		threadSentiment.run();
	}

	public void setSubject(String subject) {
		this.subject = subject;
		checkCompletion("subject");
	}

	public void setCategory(String category) {
		this.category = category;
		checkCompletion("category");
	}
	
	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
		checkCompletion("sentiment");
	}
	
	private void checkCompletion(String which) {
		if (subject != null && category != null && sentiment != null)
		{
			listener.completeDotCreated(new Dot(subject, category, sentiment));
		}
	}

}
