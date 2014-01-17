package com.asynchrony.nlp.train;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.asynchrony.nlp.db.Phrase;
import com.asynchrony.nlp.db.SQLite;

public class TrainFileWriter {
	
	private static final String CR_LF = "\r\n";
	private static final String FILE_SEP = File.separator;
	private static final String FILE_DIR = "C:" + FILE_SEP + 
									"tmp" + FILE_SEP + "nlp-train";
	private static final String FILE_DICTIONARY = "dictionary.txt";
	private static final String FILE_SENTIMENT = "sentiment_labels.txt";
	private static final String FILE_DATASET = "datasetSentences.txt";
	private static final String FILE_DATASET_SPLIT = "datasetSplit.txt";
	private static final String FILE_SOSTR = "SOStr.txt";
	private static final String FILE_STREE = "STree.txt";
	private static final String DELIMETER_PIPE = "|";
	private static final String DELIMETER_TAB = "\t";
	private static final String DELIMETER_COMMA = ",";
	private SQLite sqlite;

	public TrainFileWriter(SQLite sqlite) {
		this.sqlite = sqlite;
	}
	
	public boolean writeSetToFile()
	{
		checkFileDirectory();
		ArrayList<Phrase> phrases = sqlite.getPhrasesFromDb();
		try {
			writeDictionary(phrases);
			writeSentiments(phrases);
			writeDatasetSentences(phrases);
			writeDatasetSplit(phrases);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void writeSOStrFile(ArrayList<Phrase> phrases) throws IOException {
		String fileName = FILE_DIR + FILE_SEP + FILE_SOSTR;
		File file = new File(fileName);
		file.createNewFile();
		FileWriter writer = new FileWriter(file);
		for (Phrase phrase : phrases) {
			StringBuilder b = new StringBuilder();
			b.append(phrase.getId())
				.append(DELIMETER_TAB)
				.append(prepareSentenceForFile(phrase.getSentence()));
			writer.write(b.append(CR_LF).toString());
			writer.flush();
		}
		writer.close();
	}

	private void writeDatasetSentences(ArrayList<Phrase> phrases) throws IOException {
		String fileName = FILE_DIR + FILE_SEP + FILE_DATASET;
		File file = new File(fileName);
		file.createNewFile();
		FileWriter writer = new FileWriter(file);
		for (Phrase phrase : phrases) {
			StringBuilder b = new StringBuilder();
			b.append(phrase.getId())
			.append(DELIMETER_TAB)
			.append(prepareSentenceForFile(phrase.getSentence()));
			writer.write(b.append(CR_LF).toString());
			writer.flush();
		}
		writer.close();
	}
	
	private void writeDatasetSplit(ArrayList<Phrase> phrases) throws IOException {
		String fileName = FILE_DIR + FILE_SEP + FILE_DATASET_SPLIT;
		File file = new File(fileName);
		file.createNewFile();
		FileWriter writer = new FileWriter(file);
		int i = 0;
		for (Phrase phrase : phrases) {
			StringBuilder b = new StringBuilder();
			b.append(phrase.getId())
			.append(DELIMETER_COMMA)
			.append(trainTestDev(i++));
			writer.write(b.append(CR_LF).toString());
			writer.flush();
		}
		writer.close();
	}
	
	private String trainTestDev(int i) {
		String strInt = "" + i;
		strInt = strInt.substring(strInt.length() - 1);
		if ("3".equals(strInt))
		{
			return "3";
		}
		else if ("2".equals(strInt))
		{
			return "2";
		}
		return "1";
	}

	private String prepareSentenceForFile(String sentence) {
		return sentence.replace("'", " '")
				.replace(".", " .")
				.replace("`", "` ");
	}

	private void writeDictionary(ArrayList<Phrase> phrases) throws IOException {
		String fileName = FILE_DIR + FILE_SEP + FILE_DICTIONARY;
		File file = new File(fileName);
		file.createNewFile();
		FileWriter writer = new FileWriter(file);
		for (Phrase phrase : phrases) {
			StringBuilder b = new StringBuilder();
			b.append(phrase.getSentence()).append(DELIMETER_PIPE).append(phrase.getId());
			writer.write(b.append(CR_LF).toString());
			writer.flush();
		}
		writer.close();
	}
	
	private void writeSentiments(ArrayList<Phrase> phrases) throws IOException {
		String fileName = FILE_DIR + FILE_SEP + FILE_SENTIMENT;
		File file = new File(fileName);
		file.createNewFile();
		FileWriter writer = new FileWriter(file);
		writer.write("phrase ids|sentiment values");
		for (Phrase phrase : phrases) {
			StringBuilder b = new StringBuilder();
			b.append(phrase.getId()).append(DELIMETER_PIPE).append(phrase.getSentiment());
			writer.write(b.append(CR_LF).toString());
			writer.flush();
		}
		writer.close();
		
	}
	
	private void checkFileDirectory() {
		File dir = new File(FILE_DIR);
		if (!dir.exists())
		{
			dir.mkdirs();
		}
	}


	

}
