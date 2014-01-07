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
	
	private boolean debug = false;
	private static final String UNKNOWN_SUBJECT = "Subject Not Found";

	private SentenceParser()
	{
		// must use getInstance for singleton
	}
	
	private static SentenceParser _instance = null;
	private static SentenceParser _instance_debug = null;
	
	private LexicalizedParser lp;

	public static SentenceParser getInstance()
	{
		if (_instance == null)
		{
			initializeInstance(false);
		}
		return _instance;
	}
	
	public static SentenceParser getDebugInstance()
	{
		if (_instance_debug == null)
		{
			initializeInstance(true);
		}
		return _instance_debug;
	}
	
	private void setDebug(boolean debug)
	{
		this.debug = debug;
	}
	
	private static void initializeInstance(boolean debug) {
		if (debug)
		{
			_instance_debug = new SentenceParser();
			_instance_debug.setDebug(true);
			_instance_debug.setLexicalizedParser(LexicalizedParser.loadModel(
				"edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz",
				"-maxLength", "80", "-retainTmpSubcategories"));
		}
		else
		{
			_instance = new SentenceParser();
			_instance.setLexicalizedParser(LexicalizedParser.loadModel(
					"edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz",
					"-maxLength", "80", "-retainTmpSubcategories"));
		}
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
		String subject =  findSubject(sentenceDepenencies, false);
		if (UNKNOWN_SUBJECT.equals(subject))
		{
			subject = findSubject(sentenceDepenencies, true);
		}
		return subject;
	}

	public String findSubject(List<SentenceDependency> parseSentence, boolean allowLowerCase) {
		ArrayList<SentenceDependency> nn_possibles = new ArrayList<SentenceDependency>();
		ArrayList<SentenceDependency> nsubj_possibles = new ArrayList<SentenceDependency>();
		ArrayList<SentenceDependency> amod_possibles = new ArrayList<SentenceDependency>();
		ArrayList<SentenceDependency> prep_possibles = new ArrayList<SentenceDependency>();
		if (debug)
		{
			System.out.println("----------------");
		}
		for (SentenceDependency sd : parseSentence) {
			if (debug)
			{
				debugOutput(sd);
			}
			if ((allowLowerCase) || (Character.isUpperCase(sd.getDependentWord().charAt(0))))
			{
				if ("nn".equalsIgnoreCase(sd.getRelationship()))
				{
					nn_possibles.add(sd);
				}
				else if ("nsubj".equalsIgnoreCase(sd.getRelationship()))
				{
					nsubj_possibles.add(sd);
				}
				else if ("amod".equalsIgnoreCase(sd.getRelationship())
						|| "advmod".equalsIgnoreCase(sd.getRelationship())
						|| "nsubjpass".equalsIgnoreCase(sd.getRelationship()))
				{
					amod_possibles.add(sd);
				}
				else if ("prep".equalsIgnoreCase(sd.getRelationship()) ||
						"dep".equalsIgnoreCase(sd.getRelationship()))
				{
					prep_possibles.add(sd);
				}
			}
		}
		if (nn_possibles.size() == 0 && nsubj_possibles.size() == 0 
				&& amod_possibles.size() == 0 && prep_possibles.size() == 0)
		{
			return UNKNOWN_SUBJECT;
		}
		return findSubjectFromPossibles(nn_possibles, nsubj_possibles, amod_possibles, prep_possibles);
	}

	private void debugOutput(SentenceDependency sd) {
		StringBuilder b = new StringBuilder();
		b.append(sd.getRelationship()).append(" ");
		b.append(sd.getDependentIndex()).append(" ");
		b.append(sd.getDependentWord()).append(" ");
		b.append(sd.getParentIndex()).append(" ");
		b.append(sd.getParentWord());
		System.out.println(b.toString());
	}

	private String findSubjectFromPossibles(
			ArrayList<SentenceDependency> nn_possibles,
			ArrayList<SentenceDependency> nsubj_possibles,
			ArrayList<SentenceDependency> amod_possibles, 
			ArrayList<SentenceDependency> prep_possibles) {
		if (nn_possibles.size() == 1)
		{
			return nn_possibles.get(0).getDependentWord();
		}
		else if (nn_possibles.size() > 1)
		{
			return getBestFitFromPossibilities(nn_possibles);
		}
		else if (nsubj_possibles.size() == 1)
		{
			return nsubj_possibles.get(0).getDependentWord();
		}
		else if (nsubj_possibles.size() > 1)
		{
			return getBestFitFromPossibilities(nsubj_possibles);
		}
		else if (amod_possibles.size() == 1)
		{
			return amod_possibles.get(0).getDependentWord();
		}
		else if (amod_possibles.size() > 1)
		{
			return getBestFitFromPossibilities(amod_possibles);
		}
		else if (prep_possibles.size() == 1)
		{
			return prep_possibles.get(0).getDependentWord();
		}
		else if (prep_possibles.size() > 1)
		{
			return getBestFitFromPossibilities(prep_possibles);
		}
		return UNKNOWN_SUBJECT;
	}

	private String getBestFitFromPossibilities(
			ArrayList<SentenceDependency> possibles) {
		SentenceDependency bestFit = possibles.get(0);
		int bestIndex = 999;
		
		for (SentenceDependency dep : possibles) {
			Character.isUpperCase(dep.getDependentWord().charAt(0)); 
			if ((dep.getDependentIndex() < bestIndex) && (Character.isUpperCase(dep.getDependentWord().charAt(0))))
			{
				bestFit = dep;
				bestIndex = dep.getDependentIndex();
			}
		}
		return bestFit.getDependentWord();
	}

}
