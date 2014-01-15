package com.asynchrony.nlp.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class ZipExploderTest {
	
	private ZipExploder testObject;

	@Test
	public void test() {
		String jarFile = "C:\\temp\\stanford-corenlp-full-2014-01-04\\stanford-corenlp-3.3.1.jar";
		String[] args = {"-jar", jarFile, "-dir", "C:\\temp\\jarExplode"};
		ZipExploder.main(args );
	}

}
