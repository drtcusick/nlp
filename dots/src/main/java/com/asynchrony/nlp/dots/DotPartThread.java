package com.asynchrony.nlp.dots;

import com.asynchrony.nlp.classifier.CategoryResult;
import com.asynchrony.nlp.classifier.ColumnDataClassifierWrap;
import com.asynchrony.nlp.classifier.Properties;
import com.asynchrony.nlp.classifier.TrainingSet;
import com.asynchrony.nlp.parser.SentenceParser;
import com.asynchrony.nlp.sentiment.CustomSentimentMapper;
import com.asynchrony.nlp.sentiment.CustomSentimentPipeline;
import com.asynchrony.nlp.sentiment.Sentiment;

public class DotPartThread implements Runnable{
	

	
	private DotCreator dotCreator;
	private String sentence;
	private String part;
	private boolean withProbability = false;
	private CustomSentimentMapper sentimentMapper = null;

	@SuppressWarnings("unused")
	private DotPartThread()
	{
		// do not use default constructor
	}
	
	public DotPartThread(DotCreator dotCreator, 
			CustomSentimentMapper sentimentMapper, 
			String sentence, String part, boolean withProbability)
	{
		this.dotCreator = dotCreator;
		this.sentence = sentence;
		this.part = part;
		this.sentimentMapper = sentimentMapper;
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
			parsePartSentiment(withProbability);
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
	
	private void parsePartSentiment(boolean withProbability)
	{
		CustomSentimentPipeline sentimenter = new CustomSentimentPipeline(sentimentMapper);
		Sentiment[] sentiments  = {Sentiment.blankSentiment()};
		try {
			sentiments = sentimenter.evaluateSentiment(sentence);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dotCreator.setSentiment(getSentimentString(sentiments[0], withProbability));
	}

	private String getSentimentString(Sentiment sentiment,
			boolean withProbability) {
		StringBuilder b = new StringBuilder();
		String sentimentStr = sentiment.getSentiment();
		b.append(sentimentStr);
		
		if (withProbability)
		{
			b.append(" - ");
			b.append(sentiment.getHistogramString());
		}
		return b.toString();
	}
	

	
}
