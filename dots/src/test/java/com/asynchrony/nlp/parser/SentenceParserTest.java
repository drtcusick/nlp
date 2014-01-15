package com.asynchrony.nlp.parser;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.asynchrony.nlp.classifier.TrainingSet;
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
		SentenceParser selectObject = SentenceParser.getDebugInstance();
		String sentence = "Sammy creatively designed the updated machine" ;
		assertEquals( "Sammy", selectObject.extractSubject(sentence));
	}
	
	@Test
	public void testFindSubject() throws Exception {
		List<TypedDependency> parseSentence = SentenceParser.getInstance().parseSentence("This is an easy sentence.");
		List<SentenceDependency> sentenceDependencies = DependencyMapper.translateTypedDependenciesToSentenceDepenencies(parseSentence);
		String result = testObject.findSubject(sentenceDependencies, false);
		assertEquals("This", result);
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
		assertEquals( "Dan", testObject.extractSubject("Dan explained a user-driven approach to visualization and modeling."));
		assertEquals( "Wilbert", testObject.extractSubject("Wilbert showed the team how visualization can help bring about remarkable change."));
		assertEquals( "Steve", testObject.extractSubject("Steve was thinking strategically by suggesting the use of natural language processing."));
		assertEquals( "Arun", testObject.extractSubject("Arun understood that there were too many steps in the process"));
		assertEquals( "Jason", testObject.extractSubject("Jason saw that the dependencies were not aligned correctly"));
		assertEquals( "Missi", testObject.extractSubject("As the bad news was delivered, everyone except Missi maintained their composure."));
		assertEquals( "Travis", testObject.extractSubject("By keeping our technology options open, Travis was thinking strategically."));
	}
	
	@Test
	public void testAllTestSentences() throws Exception {
		StringBuilder b = new StringBuilder();
		String[][] phrases = TrainingSet.TRAINING_SET_PHRASES;
		for (String[] phrase : phrases) {
			String subject = testObject.extractSubject(phrase[1]);
			b.append(subject + "  " + phrase[1]).append("\n");
		}
		System.out.println(b.toString());
	}
	
	@Test
	public void testExtractSubject_ProblematicSentences() throws Exception {
		SentenceParser selectObject = SentenceParser.getInstance();

		assertEquals( "Travis", selectObject.extractSubject("By keeping our technology options open, Travis was thinking strategically."));
		assertEquals( "Sally's", selectObject.extractSubject("Sally's proposal showed honesty in understanding her own weaknesses"));
		assertEquals( "Holly's", selectObject.extractSubject("Holly's decision to use excel to determine the cause of the issue was creative"));
		assertEquals( "Lacy", selectObject.extractSubject("Lacy perceived that there were problems that were not being addressed"));
		assertEquals( "Mark", selectObject.extractSubject("Mark was as composed as a church mouse when he lost the game."));
		assertEquals( "Bob", selectObject.extractSubject("Although the room became heated, Bob remain completely composed."));
		assertEquals( "Jason's", selectObject.extractSubject("Jason's role is to ensure that the team is thinking strategically."));
		assertEquals( "Robert's", selectObject.extractSubject("Robert's decision to include documentation showed that he was thinking strategically."));
		assertEquals( "Bob's", selectObject.extractSubject("Bob's determination would not allow him to leave the office until the task was completed."));
		assertEquals( "William's", selectObject.extractSubject("William's determination carried him through the battle."));
		assertEquals( "John", selectObject.extractSubject("The determination in John is incredible, he never gives up."));
		assertEquals( "Neil's", selectObject.extractSubject("The overriding reason we landed the contract was Neil's determination to please the client."));
		assertEquals( "Ben's", selectObject.extractSubject("In understanding the clients issues, Ben's determination never wavered."));
		assertEquals( "John", selectObject.extractSubject("The determination in John is incredible, he never gives up."));
		assertEquals( "Dave", selectObject.extractSubject("Thanks to Dave, the draft determination will protect customers from significant price shocks"));
		assertEquals( "Phil", selectObject.extractSubject("Phil couldn't motivate his project team"));
		assertEquals( "Sally", selectObject.extractSubject("The failure of Sally to provide motivation for the effort was why the project failed"));
	}
}
