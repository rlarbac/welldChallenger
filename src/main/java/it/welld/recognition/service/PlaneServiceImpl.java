package it.welld.recognition.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.springframework.stereotype.Service;

import it.welld.recognition.model.Line;
import it.welld.recognition.model.Line.LineType;
import it.welld.recognition.model.Point;

@Service
public class PlaneServiceImpl implements PlaneService {

	private static Set<Line> lines = new TreeSet<Line>();
	private static Set<Point> points = new LinkedHashSet<Point>();
	
	@Override
	public void addPoint(Point newPoint) {
				
		if (points.contains(newPoint)) {
			return;
		}
		
		points.add(newPoint);

		if (points.size() == 1) {
			return;
		}
		
		if (lines.isEmpty()) {
			
			Point firstPoint = points.iterator().next();
			isAppendNewLine(firstPoint, newPoint);
			return;
			
		}
		
		// Ao adicionar um ponto, nao sendo o primeiro, devo combinar com todas as Line possiveis...
		// I will modify the line collection, so I need to create another way...
		Line[] linesArray = lines.toArray(new Line[] {});
		for (Line line : linesArray) {
			
			Point[] points = line.getPoints().toArray(new Point[] {});
			for (Point point : points ) {
				
				if (!isAppendNewLine(point, newPoint)) {
					break;
				}
			}
			
		}
		return;		
	}
	
	private boolean isAppendNewLine(Point linePoint, Point newPoint) {

		Line newLine = createLine(linePoint, newPoint);

		Line lineSearched = findBy(newLine);

		if (lineSearched != null) {

			// Add a new point.
			lineSearched.getPoints().add(newPoint);
			return false;		
		} 

		lines.add(newLine);
		return true;
			
	}
	
	private Line createLine(Point a, Point b) { 
		
		// Verify the gradient (m) and constant (c) for the equation y = mx + c.
		double y = b.getY() - a.getY();
		double x = b.getX() - a.getX();
		
		// Gradient
		double m;
		
		// Constant
		double c;
		
		Line line = null;
		
		// Horizontal line
		if (y == 0.0) {
			m = 0.0;
			c = a.getY();
					
			line = new Line(m, c, LineType.HORIZONTAL);
			
		} // Vertical line
		  else if (x == 0.0) {
			m = Double.NaN;  
			c = a.getX();
			line = new Line(m, c, LineType.VERTICAL);
			
		} else {
			m = y / x;
			c = -m*a.getX() + a.getY(); // c = -mx + y
			line = new Line(m, c);
		}
		
		line.getPoints().add(a);
		line.getPoints().add(b);
		
		return line;
	}
	
	private Line findBy(Line line) {
		
		// This list is already ordered because it received a collection from TreeSet,
		// so, I don't need to invoke Collections.sort(...)...
		List<Line> lines = new ArrayList<Line>(PlaneServiceImpl.lines);
		
		int index = Collections.binarySearch(lines, line);
		
		// This line does not exist in the lines collection.
		// and I need to include it.
		if (index < 0) {
			return null;
		}
		
		// I get the line object.
		return lines.get(index);
	}	

	public Collection<Point> getPoints() {
		return PlaneServiceImpl.points;
	}
	
	public void clearAll() {
		PlaneServiceImpl.points.clear();
		PlaneServiceImpl.lines.clear();
	}
	
	public List<Set<Point>> getAllPointsFromEachLineByNumberOfPoints(int number) {
		
		Map<Integer, List<Set<Point>>> linesPoint = new TreeMap<Integer, List<Set<Point>>>();
		
		for (Line line : PlaneServiceImpl.lines) {
			int size = line.getPoints().size();
		
			if (linesPoint.get(size)== null) {
				linesPoint.put(size, new ArrayList<Set<Point>>());
			}
			linesPoint.get(size).add(line.getPoints());
		}
				
		List<Integer> quantities = new ArrayList<Integer>(linesPoint.keySet());
		List<Set<Point>> pointsfromEachLine = new ArrayList<Set<Point>>();
		
	    int index = Collections.binarySearch(quantities, number);
    	
	    if (index < 0) {
	    	index = Math.abs(index)-1;	    	
	    }
    	
    	for (int i = index; i < quantities.size(); i++) {
    		pointsfromEachLine.addAll(linesPoint.get(quantities.get(i)));
	    }
	    		
		return pointsfromEachLine;
	}
	
	public List<Line> getLinesByNumberOfPoints(int number) {
		
		Map<Integer, List<Line>> linesPoint = new TreeMap<Integer, List<Line>>();
		
		for (Line line : PlaneServiceImpl.lines) {
			int size = line.getPoints().size();
		
			if (linesPoint.get(size)== null) {
				linesPoint.put(size, new ArrayList<Line>());
			}
			linesPoint.get(size).add(line);
		}
				
		List<Integer> quantities = new ArrayList<Integer>(linesPoint.keySet());
		List<Line> lines = new ArrayList<Line>();
		
	    int index = Collections.binarySearch(quantities, number);
    	
	    if (index < 0) {
	    	index = Math.abs(index)-1;	    	
	    }
    	
    	for (int i = index; i < quantities.size(); i++) {
    		lines.addAll(linesPoint.get(quantities.get(i)));
	    }
	    		
		return lines;
	}	
	
}
