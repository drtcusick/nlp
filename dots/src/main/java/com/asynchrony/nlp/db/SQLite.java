package com.asynchrony.nlp.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLite {
	
	private static final String TABLE_PHRASE = "phrase";

	public ArrayList<Phrase> getPhrasesFromDb()
	{
		ArrayList<Phrase> list = new ArrayList<Phrase>();
		Connection c = SQLiteJDBC.getConnection();
	    Statement stmt = null;
	    try {
	      c.setAutoCommit(false);
	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery("SELECT * FROM " + 
	    		  						TABLE_PHRASE + ";" );
	      while ( rs.next() ) {
	         int id = rs.getInt("id");
	         String sentence = rs.getString("sentence");
	         int catId  = rs.getInt("categoryId");
	         double sentiment = rs.getDouble("sentiment");
	         list.add(new Phrase(id, sentence, catId, sentiment));
	      }
	      rs.close();
	      stmt.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    SQLiteJDBC.returnConnection(c);
		return list;
	}

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
		String sql = "DELETE FROM " + TABLE_PHRASE;
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
