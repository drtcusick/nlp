package com.asynchrony.nlp.db;

import java.sql.*;

public class SQLiteJDBC {

	public static Connection getConnection() {
		Connection c = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:data\\phrases.db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return c;
	}

	public static void returnConnection(Connection c) {
		try {
			c.close();
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}
		c = null;
	}

}
