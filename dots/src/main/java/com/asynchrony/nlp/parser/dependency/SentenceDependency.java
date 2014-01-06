package com.asynchrony.nlp.parser.dependency;

public class SentenceDependency {
	
	private String relationship = null;
	private int parentIndex = -1;
	private int dependentIndex = -1;
	private String parentWord = null;
	private String dependentWord = null;
	
	public SentenceDependency(String relationship, int parentIndex,
			String parentWord, int dependentIndex, String dependentWord) {
		this.relationship = relationship;
		this.parentIndex = parentIndex;
		this.parentWord = parentWord;
		this.dependentIndex = dependentIndex;
		this.dependentWord = dependentWord;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public int getParentIndex() {
		return parentIndex;
	}

	public void setParentIndex(int parentIndex) {
		this.parentIndex = parentIndex;
	}

	public int getDependentIndex() {
		return dependentIndex;
	}

	public void setDependentIndex(int dependentIndex) {
		this.dependentIndex = dependentIndex;
	}

	public String getParentWord() {
		return parentWord;
	}

	public void setParentWord(String parentWord) {
		this.parentWord = parentWord;
	}

	public String getDependentWord() {
		return dependentWord;
	}

	public void setDependentWord(String dependentWord) {
		this.dependentWord = dependentWord;
	}

	
	
}
