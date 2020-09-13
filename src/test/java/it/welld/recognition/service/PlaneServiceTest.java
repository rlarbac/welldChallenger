package it.welld.recognition.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.welld.recognition.model.Point;


class PlaneServiceTest {

	private PlaneService service =  new PlaneServiceImpl();
	
	@BeforeEach
	void setUp() throws Exception {
		service.clearAll();
	}
	
	@Test
	void testGetLinesByNumberOfPoints() {
		service.addPoint(new Point(0,0));
		service.addPoint(new Point(1,1));
		service.addPoint(new Point(2,2));
		service.addPoint(new Point(3,3));
		
		assertEquals(1, service.getLinesByNumberOfPoints(1).size());
		assertEquals(1, service.getLinesByNumberOfPoints(2).size());
		assertEquals(1, service.getLinesByNumberOfPoints(3).size());
		assertEquals(1, service.getLinesByNumberOfPoints(4).size());
		assertEquals(0, service.getLinesByNumberOfPoints(5).size());

	}
	
	@Test
	void testPoints() {
		service.addPoint(new Point(3,3));
		service.addPoint(new Point(2,2));		
		service.addPoint(new Point(0,0));	
		service.addPoint(new Point(3,0));
		service.addPoint(new Point(3,3));
		
		assertEquals(4, service.getPoints().size());
	}	

	/**
	 * One point does not create a line in the space.
	 */
	@Test
	void testGetLinesWithOnlyPoint() {
		service.addPoint(new Point(0,0));
		
		assertEquals(0, service.getLinesByNumberOfPoints(1).size());
	}
	
	@Test
	void testClearAll() {
		service.addPoint(new Point(3,3));
		service.addPoint(new Point(2,2));		
		service.addPoint(new Point(0,0));	
		service.addPoint(new Point(3,0));

		service.clearAll();
		
		assertEquals(0, service.getPoints().size());
		assertEquals(0, service.getLinesByNumberOfPoints(1).size());
	}
	
	@Test
	void testCslearAll() {
	    //Vertical line
		Point pA = new Point(0,1);
		Point pB = new Point(0,2);
		Point pC = new Point(0,0);
		service.addPoint(pA);
		service.addPoint(pB);
		service.addPoint(pC);

		assertEquals(1, service.getLinesByNumberOfPoints(3).size());
		assertEquals(0, service.getLinesByNumberOfPoints(4).size());

	    //Horizontal line
		Point pD = new Point(-1,2);
		Point pE = new Point(4,2);
		Point pF = new Point(2,2);
		Point pG = new Point(0,2);
		Point pH = new Point(1,2);
		service.addPoint(pD);
		service.addPoint(pE);
		service.addPoint(pF);
		service.addPoint(pG);
		service.addPoint(pH);

	    //Upward line
		Point pI = new Point(1,2);
		Point pJ = new Point(2,4);
		Point pL = new Point(-1,-2);
		Point pM = new Point(-2,-4);
		service.addPoint(pI);
		service.addPoint(pJ);
		service.addPoint(pL);
		service.addPoint(pM);

		//Downward line
		Point pN = new Point(1,4);
		Point pO = new Point(3,2);
		service.addPoint(pN);
		service.addPoint(pO);
		
		assertEquals(37, service.getLinesByNumberOfPoints(1).size());
	}
	
	

}
