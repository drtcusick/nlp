package com.asynchrony.nlp.db;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLite {

	public boolean insertPhrases(ArrayList<Phrase> phrases) {
		Connection c = SQLiteJDBC.getConnection();
		clearPhraseTable(c);
		for (Phrase phrase : phrases) {
			insertPhrase(c, phrase);
		}
		SQLiteJDBC.returnConnection(c);
		return true;
	}

	private void clearPhraseTable(Connection c) {
		Statement stmt = null;
		String sql = "DELETE FROM phrase";
		try {
			stmt = c.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	private void insertPhrase(Connection c, Phrase phrase) {
		Statement stmt = null;
		String sql = "Not Set";
		try {
			stmt = c.createStatement();
			sql = createInsertSql(phrase);
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			System.out.println("TWC error in insertPhrase");
			System.out.println(" sql was " + sql);
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

	}

	private String createInsertSql(Phrase phrase) {
		StringBuilder b = new StringBuilder();
		b.append("INSERT INTO phrase (sentence, categoryId, sentiment) ");
		b.append("VALUES (");
		b.append("\"").append(phrase.getSentence()).append("\", ");
		b.append(phrase.getCategoryId()).append(", ");
		b.append(phrase.getSentiment()).append(");");
		return b.toString();
	}

}
