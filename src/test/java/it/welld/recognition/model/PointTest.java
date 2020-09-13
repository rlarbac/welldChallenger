package it.welld.recognition.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.welld.recognition.model.Point;

class PointTest {

	@Test
	void testEqualsMethod() {
		//Keeping X axis.
		Point pA = new Point(0,1);
		Point pB = new Point(0,2);
		
		assertFalse(pA.equals(pB));
		assertFalse(pB.equals(pA));
		
		//Keeping Y axis.
		Point pC = new Point(1,2);
		Point pD = new Point(2,2);
		
		assertFalse(pC.equals(pD));
		assertFalse(pD.equals(pC));

		//X and Y totally different.
		Point pE = new Point(1,2);
		Point pF = new Point(3,4);
		
		assertFalse(pE.equals(pF));
		assertFalse(pF.equals(pE));

		// Same point
		Point pG = new Point(2,2);
		Point pH = new Point(2,2);
		
		assertTrue(pG.equals(pH));
		assertTrue(pH.equals(pG));
		
		assertTrue(pH.equals(pH));

	}
	
	@Test
	void testCompareMethod() {

		//Keeping X axis.
		Point pA = new Point(0,1);
		Point pB = new Point(0,2);
		
		assertEquals(-1, pA.compareTo(pB));
		assertEquals(1, pB.compareTo(pA));

		//Keeping Y axis.
		Point pC = new Point(1,2);
		Point pD = new Point(2,2);
		
		assertEquals(-1, pC.compareTo(pD));
		assertEquals(1, pD.compareTo(pC));
		
		//X and Y totally different.
		Point pE = new Point(1,2);
		Point pF = new Point(3,4);
		
		assertEquals(-1, pE.compareTo(pF));
		assertEquals(1, pF.compareTo(pE));
		
		// Same point
		Point pG = new Point(2,2);
		Point pH = new Point(2,2);
		
		assertEquals(0, pG.compareTo(pH));
		assertEquals(0, pH.compareTo(pG));
	}

}
