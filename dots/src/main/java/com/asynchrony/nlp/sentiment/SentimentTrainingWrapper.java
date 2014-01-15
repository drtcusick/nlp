package com.asynchrony.nlp.sentiment;

import java.util.List;
import java.util.Map;

import edu.stanford.nlp.process.PTBEscapingProcessor;
import edu.stanford.nlp.sentiment.ReadSentimentDataset;
import edu.stanford.nlp.sentiment.SentimentTraining;

public class SentimentTrainingWrapper {
	
	public boolean train()
	{
		SentimentTraining training = new SentimentTraining();
	//	training.train(sentimentModel, modelPath, trainingTrees, devTrees);
		
		return true;
	}

	
	// java -mx8g edu.stanford.nlp.sentiment.SentimentTraining -numHid 25 
	// -trainPath train.txt -devPath dev.txt -train -model model.ser.gz
	
	/*
	SentimentTraining
	public static void train(SentimentModel model,
	         String modelPath,
	         List<Tree> trainingTrees,
	         List<Tree> devTrees)
	         */
	
	// ReadSentimentDataset
	
	public boolean createFormattedDataSet()
	{
		/*java edu.stanford.nlp.sentiment.ReadSentimentDataset 
		 * -dictionary stanfordSentimentTreebank/dictionary.txt 
		 * -sentiment stanfordSentimentTreebank/sentiment_labels.txt 
		 * -tokens stanfordSentimentTreebank/SOStr.txt 
		 * -parse stanfordSentimentTreebank/STree.txt 
		 * -split stanfordSentimentTreebank/datasetSplit.txt 
		 * -train train.txt -dev dev.txt -test test.txt
The arguments are as follows:
-dictionary, -sentiment, -tokens, -parse, -split Path to the corresponding files from the dataset
-train, -dev, -test Paths for saving the corresponding output files */
		ReadSentimentDataset reader = new ReadSentimentDataset();
		
		List<Integer> parentPointers;
		List<String> sentence;
		Map<List<String>,Integer> phraseIds;
		Map<Integer,Double> sentimentScores;
		PTBEscapingProcessor escaper;
		
	 	
		//reader.convertTree(parentPointers, sentence, phraseIds, sentimentScores, escaper);
		
		return true;
	}
}
