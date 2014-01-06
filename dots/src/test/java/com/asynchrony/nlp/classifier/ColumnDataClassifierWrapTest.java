package com.asynchrony.nlp.classifier;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;


public class ColumnDataClassifierWrapTest {
	
	private ColumnDataClassifierWrap testObject;
	private HashMap<String, String> props = new HashMap<String, String>();
	private static final String PATH_LINUX = "/home/dev1/git/nlp/dots/document/examples/";
	private static final String PATH = "C:\\workspace\\nlp\\dots\\document\\examples\\";
	
	@Before
	public void setUp()
	{
		props = new HashMap<String, String>();
	}
	
	@Test
	public void testGetSentenceCategory() throws Exception {
		testObject = new ColumnDataClassifierWrap(Properties.PROPS_PHRASES, TrainingSet.TRAINING_SET_PHRASES);
		assertEquals("Determination", testObject.getSentenceCategory(
				"The determination in John is incredible, he never gives up."));
	}
	
	@Test
	public void testClassifySentence_trainByStringArrayAndUseHardCodedProps() throws Exception {
		testObject = new ColumnDataClassifierWrap(Properties.PROPS_PHRASES, TrainingSet.TRAINING_SET_PHRASES);
		assertEquals("2", testObject.classifySentence(
				"The determination in John is incredible, he never gives up.").getCategory());
		assertEquals("1", testObject.classifySentence(
				"Thinking strategically, Nate exchanged team developers on an individual basis.").getCategory());
		assertEquals("3", testObject.classifySentence(
				"Composure was maintained by all as the bad news was delivered.").getCategory());
		assertEquals("4", testObject.classifySentence(
				"Visualization and planning explain the success of this project.").getCategory());
	}
	
	@Test
	public void testClassifySentence() throws Exception {
		testObject = new ColumnDataClassifierWrap(Properties.PROPS_PHRASES, TrainingSet.TRAINING_SET_PHRASES);
		assertEquals("2", testObject.classifySentence(
				"The determination in John is incredible, he never gives up.").getCategory());
		assertEquals("1", testObject.classifySentence(
				"Thinking strategically, Nate exchanged team developers on an individual basis.").getCategory());
		assertEquals("3", testObject.classifySentence(
				"Composure was maintained by all as the bad news was delivered.").getCategory());
		assertEquals("4", testObject.classifySentence(
				"Visualization and planning explain the success of this project.").getCategory());
	}
	
	@Test
	public void testClassifySentence_categoryTextAssignedCorrectly() throws Exception {
		testObject = new ColumnDataClassifierWrap(Properties.PROPS_PHRASES, TrainingSet.TRAINING_SET_PHRASES);
		assertEquals(testObject.categories.get("2"), testObject.classifySentence(
				"The determination in John is incredible, he never gives up.").getCategoryText());
		assertEquals(testObject.categories.get("1"), testObject.classifySentence(
				"Thinking strategically, Nate exchanged team developers on an individual basis.").getCategoryText());
		assertEquals(testObject.categories.get("3"), testObject.classifySentence(
				"Composure was maintained by all as the bad news was delivered.").getCategoryText());
		assertEquals(testObject.categories.get("4"), testObject.classifySentence(
				"Visualization and planning explain the success of this project.").getCategoryText());
	}
	
//	@Test
//	public void testRunClassifier_ExternalizedWithTestFile() throws IOException
//	{
//		props.put("-prop", PATH + "phrases.prop");
//		testObject = new ColumnDataClassifierWrap(props, null);
//		testObject.runTestFromParameterSpecifiedFile();
//	}
	
//	@Test
//	public void testRunClassifier_Phrases() throws IOException
//	{
//		props.put("-prop", PATH + "phrases.prop");
//		testObject = new ColumnDataClassifierWrap(props, null);
//		testObject.runOriginalClassifierMain();
//	}
	
	@Test
	public void testRunClassifier_Cheese() throws IOException
	{
		testObject = new ColumnDataClassifierWrap(Properties.PROPS_CHEESE, TrainingSet.TRAINING_SET_PHRASES);
		testObject.runOriginalClassifierMain();
	}
	
	@Test
	public void testPropertiesToArgs() throws Exception {
		testObject = new ColumnDataClassifierWrap(props, null);
		Map<String, String> map = new TreeMap<String, String>();
		map.put("-last", "LastName");
		map.put("-first", "FirstName");
		map.put("-middle", "MiddleName");
		String[] result = testObject.propertiesToArgs(map);
		assertEquals(6, result.length);
		int i = 0;
		assertEquals("-first", result[i++]);
		assertEquals("FirstName", result[i++]);
		assertEquals("-last", result[i++]);
		assertEquals("LastName", result[i++]);
		assertEquals("-middle", result[i++]);
		assertEquals("MiddleName", result[i++]);
	}
}
