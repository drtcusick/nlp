package com.asynchrony.nlp.dots;

import org.junit.Before;
import org.junit.Test;

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
}
