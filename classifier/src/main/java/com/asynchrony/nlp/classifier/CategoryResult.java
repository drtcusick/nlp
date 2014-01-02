package com.asynchrony.nlp.classifier;

public class CategoryResult {
	
	private String category;
	private String categoryText;
	private String probability;
	
	public CategoryResult(String category, String categoryText,
			String probability) {
		this.category = category;
		this.categoryText = categoryText;
		this.probability = probability;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCategoryText() {
		return categoryText;
	}
	public void setCategoryText(String categoryText) {
		this.categoryText = categoryText;
	}
	public String getProbability() {
		return probability;
	}
	public void setProbability(String probability) {
		this.probability = probability;
	}
	
	
	

}
