package com.asynchrony.nlp.db;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.asynchrony.nlp.classifier.TrainingSet;

public class SQLiteTest {
	
	private SQLite testObject;
	
	@Before
	public void setUp()
	{
		testObject = new SQLite();
	}
	
//	@Test
//	public void testGetPhraseFromDb() throws Exception {
//		ArrayList<Phrase> list = testObject.getPhrasesFromDb();
//		for (Phrase phrase : list) {
//			System.out.println("TWC " + phrase.asString());
//		}
//	}

//	@Test
//	public void testInsertTestPhrasesIntoDb() {
//		String[][] set = TrainingSet.TRAINING_SET_PHRASES;
//		ArrayList<Phrase> phrases = new ArrayList<Phrase>();
//		for (String[] line : set) {
//			int catId = Integer.parseInt(line[0]);
//			double sentiment = Double.parseDouble(line[1]);
//			Phrase phrase = new Phrase(-1, line[2], catId , sentiment);
//			phrases.add(phrase);
//		}
//		testObject.insertPhrases(phrases);
//	}

}
