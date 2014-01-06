package com.asynchrony.nlp.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.asynchrony.nlp.parser.dependency.DependencyMapper;
import com.asynchrony.nlp.parser.dependency.SentenceDependency;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;

public class SentenceParser {
	
	private static final String UNKNOWN_SUBJECT = "Subject Not Found";

	private SentenceParser()
	{
		// must use getInstance for singleton
	}
	
	private static SentenceParser _instance = null;
	
	private LexicalizedParser lp;

	public static SentenceParser getInstance()
	{
		if (_instance == null)
		{
			initializeInstance();
		}
		return _instance;
	}
	
	private static void initializeInstance() {
		_instance = new SentenceParser();
		_instance.setLexicalizedParser(LexicalizedParser.loadModel(
				"edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz",
				"-maxLength", "80", "-retainTmpSubcategories"));
	}

	private void setLexicalizedParser(LexicalizedParser lp) {
		this.lp = lp;
	}

	public List<TypedDependency> parseSentence(String sentence)
	{
		return parseSentenceList(SentenceSplitter.sentenceAsList(sentence));
	}
	
	public List<TypedDependency> parseSentenceList(String[] sent)
	{
		TreebankLanguagePack tlp = new PennTreebankLanguagePack();
		GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
		Tree parse = lp.apply(Sentence.toWordList(sent));
		GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
		return gs.typedDependenciesCCprocessed();
	}

	public String extractSubject(String sentence) {
		List<TypedDependency> parsedSentence = parseSentence(sentence);
		List<SentenceDependency> sentenceDepenencies = DependencyMapper.translateTypedDependenciesToSentenceDepenencies(parsedSentence);
		return findSubject(sentenceDepenencies);
	}

	public String findSubject(List<SentenceDependency> parseSentence) {
		ArrayList<SentenceDependency> possibles = new ArrayList<SentenceDependency>();
		for (SentenceDependency sentenceDependency : parseSentence) {
			if ("nsubj".equalsIgnoreCase(sentenceDependency.getRelationship()))
			{
				possibles.add(sentenceDependency);
			}
		}
		if (possibles.size() == 0)
		{
			return UNKNOWN_SUBJECT;
		}
		return findSubjectFromPossibles(possibles);
	}

	private String findSubjectFromPossibles(
			ArrayList<SentenceDependency> possibles) {
		if (possibles.size() == 1)
		{
			return possibles.get(0).getDependentWord();
		}
		
		SentenceDependency bestFit = possibles.get(0);
		int bestIndex = 999;
		
		for (SentenceDependency dep : possibles) {
			Character.isUpperCase(dep.getDependentWord().charAt(0)); 
			if ((dep.getDependentIndex() < bestIndex) && (Character.isUpperCase(dep.getDependentWord().charAt(0))))
			{
				bestFit = dep;
				bestIndex = dep.getDependentIndex();
			}
//			System.out.println("TWC  " + dep.getDependentWord() + " " + dep.getParentWord() + " " + dep.getDependentIndex() );
		}
		return bestFit.getDependentWord();
	}

}
