package com.asynchrony.nlp.dots;

import com.asynchrony.nlp.classifier.ColumnDataClassifierWrap;
import com.asynchrony.nlp.classifier.Properties;
import com.asynchrony.nlp.classifier.TrainingSet;
import com.asynchrony.nlp.parser.SentenceParser;
import com.asynchrony.nlp.sentiment.CustomSentimentPipeline;

public class DotMain {

	public String processSentence(String sentence) {
		StringBuilder b = new StringBuilder();
		b.append("Input:  ").append(sentence).append("\n\n");
		b.append("Subject:  ").append(getSubject(sentence)).append("\n");
		b.append("Category:  ").append(getCategory(sentence)).append("\n");
		b.append("Sentiment:  ").append(getSentiment(sentence));
		return b.toString();
	}
	
	protected String getCategory(String sentence)
	{
		ColumnDataClassifierWrap classifier = new ColumnDataClassifierWrap(Properties.PROPS_PHRASES, TrainingSet.TRAINING_SET_PHRASES);
		String category = classifier.getSentenceCategory(sentence);
		return category;
	}
	
	protected String getSubject(String sentence)
	{
		SentenceParser parser = SentenceParser.getInstance();
		String subject = parser.extractSubject(sentence);
		return subject;
	}

	protected String getSentiment(String sentence)
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
		return sentiment;
	}
	
	public static void main(String [] args)
	{
		String sentence = getInputSentence(args);
		DotMain main = new DotMain();
		String result = main.processSentence(sentence);
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
}
