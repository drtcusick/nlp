package com.asynchrony.nlp.parser;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.asynchrony.nlp.parser.dependency.DependencyMapper;
import com.asynchrony.nlp.parser.dependency.SentenceDependency;

import edu.stanford.nlp.trees.TypedDependency;

public class SentenceParserTest {
	
	private static SentenceParser testObject = SentenceParser.getInstance();

	@Test
	public void testParseSentence() {
		String sentence = "This is an easy sentence." ;
		String expected = "[nsubj(sentence-5, This-1), cop(sentence-5, is-2), det(sentence-5, an-3), amod(sentence-5, easy-4), root(ROOT-0, sentence-5)]";

		List<TypedDependency> result = testObject.parseSentence(sentence);

		assertEquals(5, result.size());
		assertEquals(expected, result.toString());
	}

	@Test
	public void testExtractSubject() throws Exception {
		String sentence = "Bell, based in Los Angeles, " +
				"makes and distributes electronic, computer and building products" ;
		assertEquals( "Bell", testObject.extractSubject(sentence));
	}
	
	@Test
	public void testFindSubject() throws Exception {
		List<TypedDependency> parseSentence = SentenceParser.getInstance().parseSentence("This is an easy sentence.");
		List<SentenceDependency> sentenceDependencies = DependencyMapper.translateTypedDependenciesToSentenceDepenencies(parseSentence);
		String result = testObject.findSubject(sentenceDependencies);
		assertEquals("This", result);
	}

	@Test
	public void testParseSentence_multipleCommas() {
		String sentence = "Bell, based in Los Angeles, " +
				"makes and distributes electronic, computer and building products" ;
		String expected = "[nsubj(makes-8, Bell-1), nsubj(distributes-10, Bell-1), " + 
				"partmod(Bell-1, based-3), nn(Angeles-6, Los-5), " + 
				"prep_in(based-3, Angeles-6), root(ROOT-0, makes-8), " + 
				"conj_and(makes-8, distributes-10), amod(products-16, electronic-11), " + 
				"conj_and(electronic-11, computer-13), amod(products-16, computer-13), " + 
				"conj_and(electronic-11, building-15), amod(products-16, building-15), " + 
				"dobj(makes-8, products-16)]";
		
		List<TypedDependency> result = testObject.parseSentence(sentence);
		
		assertEquals(13, result.size());
		assertEquals(expected, result.toString());
		TypedDependency node = result.get(5);
	}
	
	@Test
	public void testParseSentenceList() {
		String[] sent = {"This", "is", "an", "easy", "sentence", "."} ;
		String expected = "[nsubj(sentence-5, This-1), cop(sentence-5, is-2), det(sentence-5, an-3), amod(sentence-5, easy-4), root(ROOT-0, sentence-5)]";
		
		List<TypedDependency> result = testObject.parseSentenceList(sent);
		
		assertEquals(5, result.size());
		assertEquals(expected, result.toString());
	}
	
	@Test
	public void testParseSentenceList2() {
		String[] sent = {"This", "is", "not", "an", "easy", "sentence", "."} ;
		String expected = "[nsubj(sentence-6, This-1), cop(sentence-6, is-2), neg(sentence-6, not-3), det(sentence-6, an-4), amod(sentence-6, easy-5), root(ROOT-0, sentence-6)]";
		
		List<TypedDependency> result = testObject.parseSentenceList(sent);
		
		assertEquals(6, result.size());
		assertEquals(expected, result.toString());
	}
	
	@Test
	public void testExtractSubject_MoreTestSentences() throws Exception {
		assertEquals( "Bell", testObject.extractSubject("Bell, based in Los Angeles, " +
				"makes and distributes electronic, computer and building products"));
		assertEquals( "Dan", testObject.extractSubject("Dan explained a user-driven approach to visualization and modeling."));
		assertEquals( "Wilbert", testObject.extractSubject("Wilbert showed the team how visualization can help bring about remarkable change."));
		assertEquals( "Steve", testObject.extractSubject("Steve was thinking strategically by suggesting the use of natural language processing."));
		assertEquals( "Arun", testObject.extractSubject("Arun understood that there were too many steps in the process"));
		assertEquals( "Jason", testObject.extractSubject("Jason saw that the dependencies were not aligned correctly"));
		assertEquals( "everyone", testObject.extractSubject("As the bad news was delivered, everyone except Missi maintained their composure."));
		assertEquals( "Travis", testObject.extractSubject("By keeping our technology options open, Travis was thinking strategically."));
	}
	
}
