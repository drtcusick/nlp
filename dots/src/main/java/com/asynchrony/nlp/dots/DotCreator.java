package com.asynchrony.nlp.dots;

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
	
	public DotCreator(IDotCreatorListener listener, String sentence)
	{
		this.listener = listener;
		this.sentence = sentence;
	}
	
	public DotCreator(IDotCreatorListener listener, String sentence, boolean withProbability)
	{
		this(listener, sentence);
		this.withProbability = withProbability;
	}
	
	public void launchDotCreation()
	{
		DotPartThread threadSubject = new DotPartThread(this, sentence, DOT_PART_SUBJECT);
		threadSubject.run();
		DotPartThread threadCategory = new DotPartThread(this, sentence, DOT_PART_CATEGORY, withProbability);
		threadCategory.run();
		DotPartThread threadSentiment = new DotPartThread(this, sentence, DOT_PART_SENTIMENT);
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
		System.out.println("TWC Completion check from " + which);
		System.out.println("    TWC " + subject + ", " + category + ", " + sentiment);
		if (subject != null && category != null && sentiment != null)
		{
			listener.completeDotCreated(new Dot(subject, category, sentiment));
		}
	}

}
