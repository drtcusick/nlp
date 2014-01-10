package com.asynchrony.nlp.dots;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class DotCreatorTest implements IDotCreatorListener{
	private static final String TEST_SENTENCE = "Bob was able to see that the machine was broken";
	
	private DotCreator testObject;
	private Dot dot = null;
	
	@Before
	public void setUp()
	{
		testObject = new DotCreator(this, TEST_SENTENCE);
	}

	@Test
	public void testCreateDotWithProbability() {
		testObject = new DotCreator(this, TEST_SENTENCE, true);
		this.dot = null;
		testObject.launchDotCreation();
		assertNotNull(this.dot);
		assertEquals("Bob", dot.getSubject());
		assertEquals("Problems Perceiving Them - 0.995", dot.getCategory());
		assertEquals("Neutral", dot.getSentiment());
	}

	@Test
	public void testCreateDotWithoutProbability() {
		testObject = new DotCreator(this, TEST_SENTENCE);
		this.dot = null;
		long start = System.currentTimeMillis();
		testObject.launchDotCreation();
		System.out.println("TWC time to create = " + (System.currentTimeMillis() - start));
		assertNotNull(this.dot);
		assertEquals("Bob", dot.getSubject());
		assertEquals("Problems Perceiving Them", dot.getCategory());
		assertEquals("Neutral", dot.getSentiment());
	}
	
	@Override
	public void completeDotCreated(Dot dot) {
		this.dot = dot;
	}

}
