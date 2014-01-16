package com.asynchrony.nlp.train;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;

import com.asynchrony.nlp.db.SQLite;

public class TrainFileWriterTest {
	private TrainFileWriter testObject;
	@Mock
	private SQLite mockSqlite;
	
	@Before
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);
		testObject = new TrainFileWriter(mockSqlite);
	}
	
	@Test
	public void testWritePhrasesToFile() {
		testObject = new TrainFileWriter(new SQLite());
		assertTrue(testObject.writeSetToFile());
	}

}
