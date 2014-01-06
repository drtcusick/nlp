package com.asynchrony.nlp.parser;


public class Parser {
	
	
	public void runIt()
	{
		String[] sent = {"This", "is", "an", "easy", "sentence", "."} ;
		SentenceParser parser = SentenceParser.getInstance();
		System.out.println(parser.parseSentenceList(sent));
		
	}
	
	public static void main(String[] args)
	{
		Parser parser = new Parser();
		parser.runIt();
	}
	

}
