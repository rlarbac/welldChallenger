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

}
