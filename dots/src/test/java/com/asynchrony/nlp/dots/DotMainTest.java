package com.asynchrony.nlp.dots;

import static org.junit.Assert.assertEquals;

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
stanford-corenlp-3.3.1.jar
stanford-corenlp-3.3.1-models.jar
stanford-parser.jar
stanford-parser-3.3.0-javadoc.jar
stanford-parser-3.3.0-models.jar
stanford-parser-3.3.0-sources.jar

*/
public class DotMainTest {

	private static final String TEST_SENTENCE = "Luke was able to visualize the dark side.s";
	private DotMain testObject;
	
	@Before
	public void setUp()
	{
		testObject = new DotMain();
	}
	
	@Test
	public void testCreateDotThreadedWithProbability()
	{
		String result = testObject.processSentenceThreaded(TEST_SENTENCE, true);
		System.out.println(result);
	}
	
	@Test
	public void testCreateDotThreadedWithProbabilityNeutral()
	{
		String neutralSent = "Bob attended the meeting and was attentive.";
		String result = testObject.processSentenceThreaded(neutralSent, true);
		System.out.println(result);
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
	
	@Test
	public void testGetDot() throws Exception {
		Dot dot = testObject.getDot(TEST_SENTENCE, false);
		assertEquals("Luke", dot.getSubject());
		assertEquals("Visualization", dot.getCategory());
		assertEquals("Positive", dot.getSentiment());
	}
}
