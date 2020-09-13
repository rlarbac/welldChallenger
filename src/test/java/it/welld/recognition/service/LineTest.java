package it.welld.recognition.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import it.welld.recognition.model.Line;
import it.welld.recognition.model.Line.LineType;
import it.welld.recognition.model.Point;

class LineTest {

	@Test
	void testLineType() {
		//Keeping X axis.
		Point pA = new Point(0,1);
		Point pB = new Point(0,2);
		Line lineAB = Line.newInstance(pA, pB);
		
		assertEquals(LineType.VERTICAL, lineAB.getType());
		assertTrue(Double.isNaN(lineAB.getGradient()));
		
		//Keeping Y axis.
		Point pC = new Point(1,2);
		Point pD = new Point(2,2);
		Line lineCD = Line.newInstance(pC, pD);
		
		assertEquals(LineType.HORIZONTAL, lineCD.getType());
		assertEquals(0.0, lineCD.getGradient());

		//Y is increasing.
		Point pE = new Point(1,2);
		Point pF = new Point(3,4);
		Line lineEF = Line.newInstance(pE, pF);
		
		assertEquals(LineType.UPWARD, lineEF.getType());
		assertTrue(lineEF.getGradient() > 0.0);

		//Y is decreasing.
		Point pG = new Point(1,4);
		Point pH = new Point(3,2);
		Line lineGH = Line.newInstance(pG, pH);
		
		assertEquals(LineType.DOWNWARD, lineGH.getType());
		assertTrue(lineGH.getGradient() < 0.0);
	}
	
	@Test
	void testCompareMethod() {

		//Keeping X axis - VERTICAL line.
		Point pA = new Point(0,1);
		Point pB = new Point(0,2);
		Line lineAB = Line.newInstance(pA, pB);
		
		//Keeping Y axis - HORIZONTAL line.
		Point pC = new Point(1,2);
		Point pD = new Point(2,2);
		Line lineCD = Line.newInstance(pC, pD);

		//Y is increasing - UPWARD line.
		Point pE = new Point(1,2);
		Point pF = new Point(3,4);
		Line lineEF = Line.newInstance(pE, pF);

		//Y is decreasing - DOWNWARD line.
		Point pG = new Point(1,4);
		Point pH = new Point(3,2);
		Line lineGH = Line.newInstance(pG, pH);

		assertEquals(0, lineAB.compareTo(lineAB));
		assertEquals(-1, lineAB.compareTo(lineCD));
		assertEquals(-1, lineCD.compareTo(lineEF));
		assertEquals(1, lineCD.compareTo(lineGH));
		assertEquals(1, lineEF.compareTo(lineGH));
	}

	/* Lines will be the same if they have the same gradient and constant.*/
	@Test
	void testEqualsMethod() {

		//Keeping X axis - VERTICAL line.
		Point pA = new Point(0,1);
		Point pB = new Point(0,2);
		Line lineAB = Line.newInstance(pA, pB);
		
		//Keeping X axis - VERTICAL line.
		Point pC = new Point(0,3);
		Point pD = new Point(0,4);
		Line lineCD = Line.newInstance(pC, pD);

		//Keeping Y axis - HORIZONTAL line.
		Point pE = new Point(1,0);
		Point pF = new Point(3,0);
		Line lineEF = Line.newInstance(pE, pF);
		
		//Keeping Y axis - HORIZONTAL line.
		Point pG = new Point(2,0);
		Point pH = new Point(4,0);
		Line lineGH = Line.newInstance(pG, pH);

		//Y is increasing - Other type line.
		Point pI = new Point(1,2);
		Point pJ = new Point(2,4);
		Line lineIJ = Line.newInstance(pI, pJ);

		//Y is increasing - Other type line.
		Point pL = new Point(2,4);
		Point pM = new Point(4,8);
		Line lineLM = Line.newInstance(pL, pM);

		assertTrue(lineAB.equals(lineCD));
		assertTrue(lineEF.equals(lineGH));
		assertTrue(lineIJ.equals(lineLM));
		
		assertFalse(lineAB.equals(lineEF));
		assertFalse(lineAB.equals(lineGH));
		assertFalse(lineAB.equals(lineLM));
	}
}
