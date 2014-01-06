package com.asynchrony.nlp.parser.dot;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.asynchrony.nlp.parser.dependency.Dot;
import com.asynchrony.nlp.parser.dependency.SentenceDependency;

public class DotCreatorTest {
	
	private DotCreator testObject;
	
	@Before
	public void setUp()
	{
		testObject = new DotCreator();
	}

	@Test
	public void testCreateDot_AssignsCorrectPerson() {
		Dot dot = testObject.createDot(createTestSentenceDependencyList());
		assertEquals("Steve", dot.getPerson());
	}
	
	private List<SentenceDependency> createTestSentenceDependencyList()
	{
		List<SentenceDependency> list = new ArrayList<SentenceDependency>();
		list.add(new SentenceDependency("nsubj", 2, "made", 1, "Steve"));
		list.add(new SentenceDependency("root", 0, "ROOT", 2, "made"));
		list.add(new SentenceDependency("det", 4, "mistake", 3, "a"));
		list.add(new SentenceDependency("dobj", 2, "made", 4, "mistake"));
		list.add(new SentenceDependency("det", 7, "meeting", 6, "the"));
		list.add(new SentenceDependency("prep", 2, "made", 7, "meeting"));
		return list;
	}
}
