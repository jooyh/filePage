package com.siwan.studyApp;

import org.junit.Test;

public class StringTest {


	@Test
	public void test() {
		String deletedIds = "123,123,1232353245,345345,345,345,";
		System.out.println(deletedIds.substring(0, deletedIds.length()-1));
	}
}
