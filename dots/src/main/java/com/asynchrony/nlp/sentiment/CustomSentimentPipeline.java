package com.asynchrony.nlp.sentiment;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
	public static final String SENTIMENT_VERY_POSITIVE = "Very positive";
	public static final String SENTIMENT_POSITIVE = "Positive";
	public static final String SENTIMENT_NEUTRAL = "Neutral";
	public static final String SENTIMENT_NEGATIVE = "Negative";
	public static final String SENTIMENT_VERY_NEGATIVE = "Very negative";
	public static String SENTIMENT_NAMES[] = {SENTIMENT_VERY_NEGATIVE, SENTIMENT_NEGATIVE, 
		SENTIMENT_NEUTRAL, SENTIMENT_POSITIVE, SENTIMENT_VERY_POSITIVE};

	private static final NumberFormat NF = new DecimalFormat("0.0000");

	static enum Output {
		PENNTREES, VECTORS, ROOT;
	}

	public String[] evaluateSentiment(String sentenceToEvaluate)
			throws Exception {

		// removed the following options (arg related) from the main sentiment class:
		// sentimentModel, parserModel, file, stdin, output

		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");

		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		Annotation annotation = new Annotation(sentenceToEvaluate);
		pipeline.annotate(annotation);

		ArrayList<String> results = new ArrayList<String>();
		for (CoreMap sentence : (List<CoreMap>) annotation
				.get(CoreAnnotations.SentencesAnnotation.class)) {
			Tree tree = (Tree) sentence
					.get(SentimentCoreAnnotations.AnnotatedTree.class);
			System.out.println(sentence);
			int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
			results.add(sentimentString(sentiment));
		}
		return results.toArray(new String[results.size()]);
	}
	
	public Tree[] getSentimentTree(String sentenceToEvaluate)
			throws Exception {
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");

		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		Annotation annotation = new Annotation(sentenceToEvaluate);
		pipeline.annotate(annotation);

		ArrayList<Tree> results = new ArrayList<Tree>();
		for (CoreMap sentence : (List<CoreMap>) annotation
				.get(CoreAnnotations.SentencesAnnotation.class)) {
			results.add((Tree) sentence
					.get(SentimentCoreAnnotations.AnnotatedTree.class));
		}
		return results.toArray(new Tree[results.size()]);
	}
	
	private String sentimentString(int sentiment) {
		if (sentiment < 0 || sentiment > SENTIMENT_NAMES.length)
			return (new StringBuilder()).append("Unknown sentiment label ")
					.append(sentiment).toString();
		else
			return SENTIMENT_NAMES[sentiment];
	}
	
}
