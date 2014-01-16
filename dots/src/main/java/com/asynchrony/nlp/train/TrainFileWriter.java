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
	private static final String FILE_SENTIMENT = "sentiment.txt";
	private static final String DELIMETER = ",";
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
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void checkFileDirectory() {
		File dir = new File(FILE_DIR);
		if (!dir.exists())
		{
			dir.mkdirs();
		}
	}

	private void writeDictionary(ArrayList<Phrase> phrases) throws IOException {
		String fileName = FILE_DIR + FILE_SEP + FILE_DICTIONARY;
		File file = new File(fileName);
		file.createNewFile();
		FileWriter writer = new FileWriter(file);
		for (Phrase phrase : phrases) {
			StringBuilder b = new StringBuilder();
			b.append(phrase.getId()).append(DELIMETER).append(phrase.getSentence());
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
		for (Phrase phrase : phrases) {
			StringBuilder b = new StringBuilder();
			b.append(phrase.getId()).append(DELIMETER).append(phrase.getSentiment());
			writer.write(b.append(CR_LF).toString());
			writer.flush();
		}
		writer.close();
		
	}
	
	

}
