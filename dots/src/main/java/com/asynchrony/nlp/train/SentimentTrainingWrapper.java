package com.asynchrony.nlp.train;

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
		 * -train train.txt -dev dev.txt -test test.txt */
		
		String DATA_PATH = "C:\\tmp\\nlp-treebank\\";
		String OUT_PATH = "C:\\tmp\\nlp-train\\";
		String[] args = {"-dictionary", DATA_PATH + "dictionary.txt",
					"-sentiment", DATA_PATH + "sentiment_labels.txt",
					"-tokens", DATA_PATH + "SOStr.txt",
					"-parse", DATA_PATH + "STree.txt",
					"-split", DATA_PATH + "datasetSplit.txt",
					"-train", OUT_PATH + "train.txt",
					"-dev", OUT_PATH + "dev.txt",
					"-test", OUT_PATH + "test.txt"};
		ReadSentimentDataset.main(args);
		return true;
	}
}
