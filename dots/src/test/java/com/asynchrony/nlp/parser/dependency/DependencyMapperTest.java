package com.asynchrony.nlp.parser.dependency;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.asynchrony.nlp.parser.SentenceParser;
import com.asynchrony.nlp.parser.dot.DotCreator;

import edu.stanford.nlp.trees.TypedDependency;

public class DependencyMapperTest {
	
	private static final String SENTENCE_BELL = "Bell, based in Los Angeles, " +
			"makes and distributes electronic, computer and building products";
	private static final String SENTENCE_STEVE_MISTAKE = "Steve made a mistake during the meeting.";
	private DependencyMapper testObject;
	private SentenceParser sentenceParser = null;
	
	private DotCreator dotCreator = new DotCreator();

	@Before
	public void setUp()
	{
		sentenceParser = SentenceParser.getInstance();
		testObject = new DependencyMapper(dotCreator);
	}
	
	@Test
	public void testMapParsedSentence() {
		List<TypedDependency> parsedSentence = createTestParsedSentence(SENTENCE_STEVE_MISTAKE);
		Dot dot = testObject.mapParsedSentence(parsedSentence);
		assertEquals("Steve", dot.getPerson());
	}
	
	@Test
	public void testTranslateSentence() {
		List<TypedDependency> parsedSentence = createTestParsedSentence(SENTENCE_STEVE_MISTAKE);
		List<SentenceDependency> result = testObject.translateToSentenceDependencies(parsedSentence);
		assertEquals(parsedSentence.size(), result.size());
		for (SentenceDependency dep : result) {
			StringBuilder b = new StringBuilder();
			b.append(dep.getRelationship() + ":  ");
			b.append(dep.getParentIndex() + " " + dep.getParentWord());
			b.append("    " + dep.getDependentIndex() + " " + dep.getDependentWord());
			System.out.println("TWC      " + b.toString());
		}
	}
	
	private List<TypedDependency> createTestParsedSentence(String sentence) {
		return sentenceParser.parseSentence(sentence);
	}

}
