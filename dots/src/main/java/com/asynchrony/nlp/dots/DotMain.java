package com.asynchrony.nlp.dots;

import com.asynchrony.nlp.classifier.CategoryResult;
import com.asynchrony.nlp.classifier.ColumnDataClassifierWrap;
import com.asynchrony.nlp.classifier.Properties;
import com.asynchrony.nlp.classifier.TrainingSet;
import com.asynchrony.nlp.parser.SentenceParser;
import com.asynchrony.nlp.sentiment.CustomSentimentMapper;
import com.asynchrony.nlp.sentiment.CustomSentimentPipeline;
import com.asynchrony.nlp.sentiment.Sentiment;

public class DotMain implements IDotCreatorListener {

	private Dot dot = null;

	public String processSentence(CustomSentimentMapper sentimentMapper, String sentence) {
		Dot dot = getDot(sentimentMapper, sentence, false);
		return formatResult(sentence, dot);
	}
	
	public String processSentenceWithProbability(CustomSentimentMapper sentimentMapper, String sentence) {
		Dot dot = getDot(sentimentMapper, sentence, true);
		return formatResult(sentence, dot);
	}
	
	public String processSentenceThreaded(CustomSentimentMapper sentimentMapper, 
			String sentence, boolean withProbability)
	{
		Dot createDotThreaded = createDotThreaded(sentimentMapper, sentence, withProbability);
		return formatResult(sentence, createDotThreaded);
	}
	
	private String formatResult(String sentence, Dot dotToFormat) {
		StringBuilder b = new StringBuilder();
		b.append("Input:  ").append(sentence).append("\n\n");
		b.append("Subject:  ").append(dotToFormat.getSubject()).append("\n");
		b.append("Category:  ").append(dotToFormat.getCategory()).append("\n");
		b.append("Sentiment:  ").append(dotToFormat.getSentiment());
		return b.toString();
	}
	
	protected Dot createDotThreaded(CustomSentimentMapper sentimentMapper, 
			String sentence, boolean withProb)
	{
		this.dot = null;
		DotCreator creator = new DotCreator(this, sentimentMapper, sentence, withProb);
		creator.launchDotCreation();
		while (this.dot == null) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return this.dot;
	}
	
	protected Dot getDot(CustomSentimentMapper sentimentMapper, String sentence, boolean withProb)
	{
		String subject = getSubject(sentence);
		String category = getCategory(sentence, withProb);
		String sentiment = getSentiment(sentimentMapper, sentence);
		
		return new Dot(subject, category, sentiment);
	}
	
	protected String getCategory(String sentence, boolean withProb)
	{
		ColumnDataClassifierWrap classifier = new ColumnDataClassifierWrap(Properties.PROPS_PHRASES, TrainingSet.TRAINING_SET_PHRASES);
		CategoryResult category = classifier.classifySentence(sentence);
		if (withProb)
		{
			return category.getCategoryText() + " - " + category.getProbability();
		}
		else
		{
			return category.getCategoryText();
		}
	}
	
	protected String getSubject(String sentence)
	{
		SentenceParser parser = SentenceParser.getInstance();
		String subject = parser.extractSubject(sentence);
		return subject;
	}

	protected String getSentiment(CustomSentimentMapper sentimentMapper, String sentence)
	{
		String sentiment = "Unknown";
		CustomSentimentPipeline sentimenter = new CustomSentimentPipeline(sentimentMapper);
		Sentiment[] sentiments;
		try {
			sentiments = sentimenter.evaluateSentiment(sentence);
			sentiment = sentiments[0].getSentiment();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sentiment;
	}
	
	public static void main(String [] args)
	{
		String sentence = getInputSentence(args);
		DotMain main = new DotMain();
		CustomSentimentMapper sentimentMapper = new CustomSentimentMapper();
		String result = main.processSentence(sentimentMapper, sentence);
		System.out.println(result);
	}

	private static String getInputSentence(String[] args) {
		String sentence = "Input sentence must be first or second argument.";
		if (args == null || args.length == 0)
		{
			return sentence;
		}
		if (args.length == 1)
		{
			sentence = args[0];
		}
		else if (args.length == 2)
		{
			sentence = args[1];
		}
		return sentence;
	}

	@Override
	public void completeDotCreated(Dot dot) {
		this.dot  = dot;
	}
}
