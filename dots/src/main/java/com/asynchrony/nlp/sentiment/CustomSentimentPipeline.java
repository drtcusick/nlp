package com.asynchrony.nlp.sentiment;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.ejml.data.DenseMatrix64F;
import org.ejml.simple.SimpleMatrix;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.Label;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentModel;
import edu.stanford.nlp.sentiment.SentimentUtils;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

public class CustomSentimentPipeline {
	public static final String HISTOGRAM_VALUE_FORMAT = "%6.3f ";
	public static final String SENTIMENT_VERY_POSITIVE = "Very positive";
	public static final String SENTIMENT_POSITIVE = "Positive";
	public static final String SENTIMENT_NEUTRAL = "Neutral";
	public static final String SENTIMENT_NEGATIVE = "Negative";
	public static final String SENTIMENT_VERY_NEGATIVE = "Very negative";
	public static String SENTIMENT_NAMES[] = {SENTIMENT_VERY_NEGATIVE, SENTIMENT_NEGATIVE, 
		SENTIMENT_NEUTRAL, SENTIMENT_POSITIVE, SENTIMENT_VERY_POSITIVE};

	private static final NumberFormat NF = new DecimalFormat("0.0000");
	private CustomSentimentMapper customMapper;

	static enum Output {
		PENNTREES, VECTORS, ROOT;
	}

	
	public CustomSentimentPipeline(CustomSentimentMapper customMapper)
	{
		this.customMapper = customMapper;
	}
	
	public Sentiment[] evaluateSentiment(String sentenceToEvaluate)
			throws Exception {
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");

		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		Annotation annotation = new Annotation(sentenceToEvaluate);
		pipeline.annotate(annotation);

		ArrayList<Sentiment> results = new ArrayList<Sentiment>();
		for (CoreMap sentence : (List<CoreMap>) annotation
				.get(CoreAnnotations.SentencesAnnotation.class)) {
			Tree tree = (Tree) sentence
					.get(SentimentCoreAnnotations.AnnotatedTree.class);
			String sentiment = sentimentString(RNNCoreAnnotations.getPredictedClass(tree));
			SimpleMatrix mat = RNNCoreAnnotations.getPredictions(tree);
			Sentiment rawSentiment = createSentimentObject(sentiment, mat);
			results.add(adjustSentiment(rawSentiment));
		}
		return results.toArray(new Sentiment[results.size()]);
	}
	
	private Sentiment adjustSentiment(Sentiment rawSentiment)
	{
		if (customMapper == null)
		{
			return rawSentiment;
		}
		return customMapper.adjustRawSentiment(rawSentiment);
	}
	
	public Sentiment createSentimentObject(String sentiment, SimpleMatrix mat) {
		DenseMatrix64F matrix64f = mat.getMatrix();
		ArrayList<String> groupProb = new ArrayList<>();
		for (int row = 0; row < matrix64f.numRows; row++) {
				groupProb.add(String.format(HISTOGRAM_VALUE_FORMAT, new Object[] { Double
								.valueOf(matrix64f.get(row, 0)) }));
		}
		String[] histogram = groupProb.toArray(new String[groupProb.size()]);
		return new Sentiment(sentiment, histogram);
	}

	private String sentimentString(int sentiment) {
		if (sentiment < 0 || sentiment > SENTIMENT_NAMES.length)
			return (new StringBuilder()).append("Unknown sentiment label ")
					.append(sentiment).toString();
		else
			return SENTIMENT_NAMES[sentiment];
	}
	
}
