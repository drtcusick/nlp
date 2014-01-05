package com.asynchrony.nlp.parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class SentenceSplitterTest {
	
	

	@Test
	public void testSentenceAsList_simpleNoPunctuation() {
		String[] sentenceAsList = SentenceSplitter.sentenceAsList("Not yet implemented");
		assertEquals(3, sentenceAsList.length);
		assertEquals("Not", sentenceAsList[0]);
		assertEquals("yet", sentenceAsList[1]);
		assertEquals("implemented", sentenceAsList[2]);
	}

	@Test
	public void testSentenceAsList_simpleWithPeriod() {
		String[] sentenceAsList = SentenceSplitter.sentenceAsList("Not yet implemented.");
		assertEquals(4, sentenceAsList.length);
		assertEquals("Not", sentenceAsList[0]);
		assertEquals("yet", sentenceAsList[1]);
		assertEquals("implemented", sentenceAsList[2]);
		assertEquals(".", sentenceAsList[3]);
	}
	
	@Test
	public void testSentenceAsList_simpleWithComma() {
		String[] sentenceAsList = SentenceSplitter.sentenceAsList("Not yet, implement it.");
		assertEquals(6, sentenceAsList.length);
		int i = 0;
		assertEquals("Not", sentenceAsList[i++]);
		assertEquals("yet", sentenceAsList[i++]);
		assertEquals(",", sentenceAsList[i++]);
		assertEquals("implement", sentenceAsList[i++]);
		assertEquals("it", sentenceAsList[i++]);
		assertEquals(".", sentenceAsList[i++]);
	}
	
	@Test
	public void testSentenceAsList_simpleWithSemiComma() {
		String[] sentenceAsList = SentenceSplitter.sentenceAsList("implement it; now.");
		assertEquals(5, sentenceAsList.length);
		int i = 0;
		assertEquals("implement", sentenceAsList[i++]);
		assertEquals("it", sentenceAsList[i++]);
		assertEquals(";", sentenceAsList[i++]);
		assertEquals("now", sentenceAsList[i++]);
		assertEquals(".", sentenceAsList[i++]);
	}
	
	@Test
	public void testSentenceAsList_simpleWithMultipleCommas() {
		String[] sentenceAsList = SentenceSplitter.sentenceAsList("Bell, based in Los Angeles, " +
			"makes and distributes electronic, computer and building products");
		assertEquals(16, sentenceAsList.length);
		int i = 0;
		assertEquals("Bell", sentenceAsList[i++]);
		assertEquals(",", sentenceAsList[i++]);
		assertEquals("based", sentenceAsList[i++]);
		assertEquals("electronic", sentenceAsList[10]);
		assertEquals(",", sentenceAsList[11]);
		assertEquals("products", sentenceAsList[15]);
	}
	
}
