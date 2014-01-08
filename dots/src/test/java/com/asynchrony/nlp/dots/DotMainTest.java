package com.asynchrony.nlp.dots;

import org.junit.Before;
import org.junit.Test;
/*
 REQUIRED LIBRARIES:
ejml-0.23.jar
junit.jar
org.hamcrest.core_1.1.0.v20090501071000.jar
stanford-classifier.jar
stanford-classifier-3.3.0.jar
stanford-classifier-3.3.0-javadoc.jar
stanford-classifier-3.3.0-sources.jar
stanford-corenlp-3.3.0.jar
stanford-corenlp-3.3.0-models.jar
stanford-parser.jar
stanford-parser-3.3.0-javadoc.jar
stanford-parser-3.3.0-models.jar
stanford-parser-3.3.0-sources.jar

*/
public class DotMainTest {

	private static final String TEST_SENTENCE = "Bob was able to see that the machine was broken";
	private DotMain testObject;
	
	@Before
	public void setUp()
	{
		testObject = new DotMain();
	}
	
	@Test
	public void testMain()
	{
		String result = testObject.processSentence(TEST_SENTENCE);
		System.out.println(result);
	}
	
	@Test
	public void testMainWithProb()
	{
		String result = testObject.processSentenceWithProbability(TEST_SENTENCE);
		System.out.println(result);
	}
}
