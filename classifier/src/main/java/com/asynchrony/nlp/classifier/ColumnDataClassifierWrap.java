package com.asynchrony.nlp.classifier;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import edu.stanford.nlp.classify.Classifier;
import edu.stanford.nlp.classify.ColumnDataClassifier;
import edu.stanford.nlp.classify.ColumnDataClassifierExt;
import edu.stanford.nlp.classify.GeneralDataset;
import edu.stanford.nlp.classify.LinearClassifier;
import edu.stanford.nlp.classify.LogisticClassifier;
import edu.stanford.nlp.classify.ColumnDataClassifierExt.Flags;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.util.StringUtils;

public class ColumnDataClassifierWrap {

	private static final CategoryResult UNKNOWN_CATEGORY_RESULT = 
				new CategoryResult("-1", TrainingSet.UNKNOWN, "0.0");
	private Map<String, String> props;
	protected Map<String, String> categories;
	private static final Pattern tab = Pattern.compile("\\t");
	
	private ColumnDataClassifierExt cdc;

	public ColumnDataClassifierWrap(String allProperties[][], String[][] trainSet)
	{
		this.props = new HashMap<String, String>();
		for (String[] property : allProperties) {
			this.props.put("-" + property[0], property[1]);
		}
		constructClassifierExtended(trainSet);
	}
	
	public ColumnDataClassifierWrap(Map<String, String> props, String[][] trainSet) {
		this.props = props;
		constructClassifierExtended(trainSet);
	}
	
	private void constructClassifierExtended(String[][] trainSet)
	{
		String[] args = propertiesToArgs(props);
		cdc = new ColumnDataClassifierExt(
				StringUtils.argsToProperties(args));
		if (trainSet == null || trainSet.length < 1)
		{
			trainClassifierFromFile();
		}
		else
		{
			trainClassifierFromList(trainSet);
		}
		setCategories();
	}
	
	public String getSentenceCategory(String sentence)
	{
		return classifySentence(sentence).getCategoryText();
	}

	public CategoryResult classifySentence(String sentence)
	{
		String result  = "-1";
		result = cdc.classifySentence("4", sentence);
		if (result == null)
		{
			return UNKNOWN_CATEGORY_RESULT;
		}
		String[] split = tab.split(result);
		if (split.length < 2)
		{
			return UNKNOWN_CATEGORY_RESULT;
		}
		String category = split[0];
		String probability = split[1];
		String categoryStr = categories.get(category);
		System.out.println("TWC category and probability = " + category + " --- " + categoryStr + " --- " + probability);
		return new CategoryResult(category, categoryStr, probability);
	}
	
	public void runTestFromParameterSpecifiedFile() {
			testFromFile();
	}

	private void testFromFile() {
		String testFile = cdc.globalFlags.testFile;
		System.out.println("TWC testFile = " + testFile);
		if (testFile != null)
			cdc.testClassifier(testFile);
	}
	
	private void trainClassifierFromList(String[][] lines)
	{
		try {
			cdc.trainClassifierFromList(lines);
		} catch (IOException e) {
			System.out.println("TWC OOPS we threw an exception training.");
			e.printStackTrace();
		}
	}

	private void trainClassifierFromFile() {
		boolean trainClassifier = false;
		try {
			trainClassifier = cdc.trainClassifierFromFile();
		} catch (IOException e) {
			System.out.println("TWC OOPS we threw an exception training.");
			e.printStackTrace();
		}
	}
	
	protected String[] propertiesToArgs(Map<String, String> props) {
		ArrayList<String> argList = new ArrayList<String>();
		Set<String> keySet = props.keySet();
		for (String key : keySet) {
			argList.add(key);
			argList.add(props.get(key));
		}
		return argList.toArray(new String[argList.size()]);
	}
	
	public void runOriginalClassifierMain() throws IOException {
		String[] args = propertiesToArgs(props);
		ColumnDataClassifier.main(args);
	}

	private void setCategories() {
		categories = new HashMap<String, String>();
		for (String[] category : TrainingSet.TRAINING_CATEGORIES) {
			categories.put(category[0], category[1]);
		}
	}



}
