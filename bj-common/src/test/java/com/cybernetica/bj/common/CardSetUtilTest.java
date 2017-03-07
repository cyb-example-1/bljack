package com.cybernetica.bj.common;

import static org.junit.Assert.*;

import org.junit.Test;

public class CardSetUtilTest {
	
	private static long ACEx2=0x1000<<13|0x1000;
	private static long THREE=0x2;
	private static long FIVE=0x8;

	@Test
	public void testScore_1() {
		//score of 8  + 2xace should be 20
		assertEquals(20,CardSetUtils.getSetScore(FIVE|THREE|ACEx2));
	}
	
	@Test
	public void testScore_2() {
		//score of 8  + 2xace should be 20
		assertEquals(20,CardSetUtils.getSetScore(FIVE|THREE|ACEx2));
	}

}
