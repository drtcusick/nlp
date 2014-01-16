package com.asynchrony.nlp.db;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

public class SQLiteJDBCTest {

	@Test
	public void testTestOpenCloseDb() throws SQLException {
		Connection c = SQLiteJDBC.getConnection();
		assertNotNull(c);
		assertFalse(c.isClosed());
		SQLiteJDBC.returnConnection(c);
		assertTrue(c.isClosed());
	}

}
