package com.asynchrony.nlp.parser.dot;

import java.util.List;

import com.asynchrony.nlp.parser.dependency.Dot;
import com.asynchrony.nlp.parser.dependency.SentenceDependency;

public class DotCreator {

	public Dot createDot(
			List<SentenceDependency> createTestSentenceDependencyList) {
		Dot dot = new Dot();
		dot.setPerson("Steve");
		dot.setCategory("Intuitive");
		dot.setPositive(false);
		return dot;
	}

}
