package se.lnu.eres.evidence.util;


import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class CombinationManagerDoubleTest {

	private static final Logger Logger = LogManager.getLogger(CombinationManagerDoubleTest.class.getSimpleName());
	
	double[][] elements = { { 1.0, 2.0, 3.0, 4.0 }, { 5.0, 6.0, 7.0, 8.0 }, { 9.0, 10.0, 11.0, 12.0 } };

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test() {
		CombinationManagerDouble c = new CombinationManagerDouble(elements);
		Assertions.assertEquals( Arrays.asList(new Double[] {1.0, 5.0, 9.0}), c.getNextCombination());
		Logger.trace("Elements read in the second combination are: {}", c.getNextCombination().toString());
		Logger.trace("Elements read in the third combination are: {}", c.getNextCombination().toString());
		Logger.trace("Elements read in the fourth combination are: {}", c.getNextCombination().toString());
		Logger.trace("Elements read in the fift combination are: {}", c.getNextCombination().toString());
		Assertions.assertEquals(Arrays.asList(new Double[] {1.0, 6.0, 10.0}), c.getNextCombination());
		
		
	}

}
