package com.asynchrony.nlp.dots;

import com.asynchrony.nlp.classifier.CategoryResult;
import com.asynchrony.nlp.classifier.ColumnDataClassifierWrap;
import com.asynchrony.nlp.classifier.Properties;
import com.asynchrony.nlp.classifier.TrainingSet;
import com.asynchrony.nlp.parser.SentenceParser;
import com.asynchrony.nlp.sentiment.CustomSentimentPipeline;

public class DotPartThread implements Runnable{
	

	
	private DotCreator dotCreator;
	private String sentence;
	private String part;
	private boolean withProbability = false;

	@SuppressWarnings("unused")
	private DotPartThread()
	{
		// do not use default constructor
	}
	
	public DotPartThread(DotCreator dotCreator, String sentence, String part)
	{
		this.dotCreator = dotCreator;
		this.sentence = sentence;
		this.part = part;
	}

	public DotPartThread(DotCreator dotCreator, String sentence, String part, boolean withProbability)
	{
		this(dotCreator, sentence, part);
		this.withProbability = withProbability;
	}
	
	@Override
	public void run() {
		if (DotCreator.DOT_PART_SUBJECT.equals(part))
		{
			parsePartSubject();
		}
		else if (DotCreator.DOT_PART_CATEGORY.equals(part))
		{
			parsePartCategory(withProbability);
		}
		else if (DotCreator.DOT_PART_SENTIMENT.equals(part))
		{
			parsePartSentiment();
		}
		else
		{
			System.err.println("ERROR:  BAD USAGE OF DotPartThread!!!");
		}
		
	}
	
	private void parsePartSubject()
	{
		SentenceParser parser = SentenceParser.getInstance();
		String subject = parser.extractSubject(sentence);
		dotCreator.setSubject(subject);
	}
	
	private void parsePartCategory(boolean withProb)
	{
		ColumnDataClassifierWrap classifier = new ColumnDataClassifierWrap(Properties.PROPS_PHRASES, TrainingSet.TRAINING_SET_PHRASES);
		CategoryResult category = classifier.classifySentence(sentence);
		if (withProb)
		{
			dotCreator.setCategory(
					category.getCategoryText() + " - " + category.getProbability());
		}
		else
		{
			dotCreator.setCategory(
					category.getCategoryText());
		}
	}
	
	private void parsePartSentiment()
	{
		String sentiment = "Unknown";
		CustomSentimentPipeline sentimenter = new CustomSentimentPipeline();
		String[] sentiments;
		try {
			sentiments = sentimenter.evaluateSentiment(sentence);
			sentiment = sentiments[0];
		} catch (Exception e) {
			e.printStackTrace();
		}
		dotCreator.setSentiment(sentiment);
	}
	

	
}
