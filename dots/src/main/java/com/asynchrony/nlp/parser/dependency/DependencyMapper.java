package com.asynchrony.nlp.parser.dependency;

import java.util.ArrayList;
import java.util.List;

import com.asynchrony.nlp.parser.dot.DotCreator;

import edu.stanford.nlp.trees.TypedDependency;

public class DependencyMapper {
	
	private DotCreator dotCreator;

	public DependencyMapper(DotCreator dotCreator)
	{
		this.dotCreator = dotCreator;
	}

	public Dot mapParsedSentence(List<TypedDependency> parsedSentence) {
		List<SentenceDependency> translated = translateToSentenceDependencies(parsedSentence);
		return dotCreator.createDot(translated);
	}
	
	public static List<SentenceDependency> translateTypedDependenciesToSentenceDepenencies(List<TypedDependency> parsedSentence)
	{
		return new DependencyMapper(null).translateToSentenceDependencies(parsedSentence);
	}
	
	public List<SentenceDependency> translateToSentenceDependencies(List<TypedDependency> parsedSentence)
	{
		List<SentenceDependency> list = new ArrayList<SentenceDependency>();
		for (TypedDependency typedDep : parsedSentence) {
			SentenceDependency dep = new SentenceDependency(typedDep.reln().getShortName(), 
					typedDep.gov().index(), typedDep.gov().nodeString(), 
					typedDep.dep().index(), typedDep.dep().nodeString());
			list.add(dep);
		}
		return list;
	}
	
	

}
