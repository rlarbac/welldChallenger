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
	
	/**
	 * The method add a new point to the space.
	 * @return boolean if a point was added, it returns true, otherwise false.
	 */
	@Override
	public boolean addPoint(Point newPoint) {
				
		if (points.contains(newPoint)) {
			return false;
		}
		
		points.add(newPoint);

		if (points.size() == 1) {
			return true;
		}
		
		if (lines.isEmpty()) {
			
			Point firstPoint = points.iterator().next();
			isAppendNewLine(firstPoint, newPoint);
			return true;
			
		}
		
		// Upon adding a new point to the space, if it is not the first one, 
		// I have to verify all the possibilities in order to build a new line
		// or to be added to existed one.
		Line[] linesArray = lines.toArray(new Line[] {});
		for (Line line : linesArray) {
			
			Point[] points = line.getPoints().toArray(new Point[] {});
			for (Point point : points ) {
				
				if (!isAppendNewLine(point, newPoint)) {
					break;
				}
			}
			
		}
		return true;		
	}
	
	/**
	 * The method receives a point from an existing line and a new point,
	 * it verifies whether the line with these points already exists or not.
	 * if it is already exists, the method add a new point to an existent line. In that case, returns false.
	 * otherwise, it adds a new line to the space, in that case, returns true.   
	 * @param linePoint a point from an existent line.
	 * @param newPoint a new point
	 * @return boolean if a line was added, it returns true. Otherwise, false. 
	 */
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
	
	/**
	 * The method had the responsibility to create an object Line with the point a and b.
	 * In the end,it creates an object line with the gradient, constant and type (LineType enum) from the Y = MX + C equation.  
	 * @param a first point.
	 * @param b second point.
	 * @return an object Line.
	 */
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
	
	/**
	 * The method check if a line already exists in the space. 
	 * In other words, if there is a line with the same gradient, constant and type.
	 * If the line exists, it returns the line.
	 * Otherwise, it returns null.
	 * @param line A line that the method searches.
	 * @return the line searched or null
	 */
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

	/**
	 * The method returns a list of all points from the space.
	 * @return a list of all points from the space.
	 */
	@Override
	public Collection<Point> getPoints() {
		return PlaneServiceImpl.points;
	}
	
	/**
	 * The method removes all the points from the space. In the end, the space will be empty.
	 */
	@Override
	public void clearAll() {
		PlaneServiceImpl.points.clear();
		PlaneServiceImpl.lines.clear();
	}
	
	/**
	 * The method returns all points from each line that has at least the number of points given by the parameter named as "number".
	 * @param number the maximum number of points that the line must have.
	 * @return list It returns a list of set of points from each line.
	 */ 
	@Override
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
	
	/**
	 * The method returns all lines (with their properties - gradient, constant and type)
	 * that have at least the number of points given by the parameter named as "number".
	 * @param number the maximum number of points that the line must have.
	 * @return list It returns a list of lines.
	 */ 
	@Override
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
