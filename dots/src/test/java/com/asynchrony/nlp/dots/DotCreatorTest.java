package com.asynchrony.nlp.dots;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.asynchrony.nlp.sentiment.CustomSentimentMapper;

public class DotCreatorTest implements IDotCreatorListener{
	private static final String TEST_SENTENCE = "Bob was able to see that the machine was broken";
	
	private DotCreator testObject;
	private Dot dot = null;
	CustomSentimentMapper sentimentMapper = new CustomSentimentMapper();
	
	@Before
	public void setUp()
	{
		testObject = new DotCreator(this, sentimentMapper, TEST_SENTENCE, false);
	}

	@Test
	public void testCreateDotWithProbability() {
		testObject = new DotCreator(this, sentimentMapper, TEST_SENTENCE, true);
		this.dot = null;
		testObject.launchDotCreation();
		assertNotNull(this.dot);
		assertEquals("Bob", dot.getSubject());
		assertEquals("Problems Perceiving Them - 0.995", dot.getCategory());
		assertTrue(dot.getSentiment().startsWith("Positive"));
	}

	@Test
	public void testCreateDotWithProbability_NeutralChangedToNegative() {
		String testSent = "Bob attended the meeting and was attentive.";
		testObject = new DotCreator(this, sentimentMapper, testSent, true);
		this.dot = null;
		testObject.launchDotCreation();
		assertNotNull(this.dot);
		assertEquals("Bob", dot.getSubject());
		assertEquals("Problems Perceiving Them - 0.309", dot.getCategory());
		assertTrue(dot.getSentiment().startsWith("Negative"));
	}
	
	@Test
	public void testCreateDotWithoutProbability() {
		testObject = new DotCreator(this, sentimentMapper, TEST_SENTENCE, false);
		this.dot = null;
		testObject.launchDotCreation();
		assertNotNull(this.dot);
		assertEquals("Bob", dot.getSubject());
		assertEquals("Problems Perceiving Them", dot.getCategory());
		assertEquals("Positive", dot.getSentiment());
	}
	
	@Override
	public void completeDotCreated(Dot dot) {
		this.dot = dot;
	}

}
