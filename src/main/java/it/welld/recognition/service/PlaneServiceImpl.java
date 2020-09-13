package it.welld.recognition.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.springframework.stereotype.Service;

import it.welld.recognition.model.Line;
import it.welld.recognition.model.Point;

@Service
public class PlaneServiceImpl implements PlaneService {

	private static Set<Line> lines = new TreeSet<Line>();
	private static Set<Point> points = new LinkedHashSet<Point>();
	
	/**
	 * The method add a new point to the space. If the point already exists in the space, it returns false.
	 * @return boolean if a point was added to the space, it returns true, otherwise false.
	 */
	@Override
	public synchronized boolean addPoint(Point newPoint) {
				
		if (points.contains(newPoint)) {
			return false;
		}
		
		points.add(newPoint);

		if (points.size() == 1) {
			return true;
		}
		
		if (lines.isEmpty()) {
			
			Point firstPoint = points.iterator().next();
			Line newLine = Line.newInstance(firstPoint, newPoint);
			lines.add(newLine);
			return true;
			
		}
		
		afterTheSecondPoint(newPoint);
		
		return true;		
	}
	
	/**
	 * After the second point, it is necessary to check all straight-line equations in the space. 
	 * In other words, if this new point and one already in the space do not build a new straight-line equation,
	 * the new point should be added an existing line (i.e. the same straight-line equation). 
	 * Otherwise, It should be created a new line in the space with this set of points (the new point and the existing one). 
	 * @param newPoint a new point
	 */
	private void afterTheSecondPoint(Point newPoint) {

		// First, the below block check all necessary modifications.
		List<Line> newLines = new ArrayList<Line>();
		Map<Line, Point> lineAndPoint = new HashMap<Line, Point>();

		for (Line line : lines) {
			
			for (Point point : line.getPoints() ) {
				
				Line newLine = Line.newInstance(point, newPoint);
				Line lineSearched = findBy(newLine);
				
				if (lineSearched != null) {
					lineAndPoint.put(lineSearched, newPoint);
					break;
					
				} else {
					newLines.add(newLine);
				}
			}
		}
		
		// Then, it is crucial to commit the modifications...
		lines.addAll(newLines);
		
		for (Line line : lineAndPoint.keySet()) {
			line.getPoints().add(lineAndPoint.get(line));
		}			
	}
	
	/**
	 * The method searches if the given line by the parameter "line" already exists in the space. 
	 * In other words, if there is a line with the same straight-line equation (gradient and constant), it returns this line.
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
	 * The method removes all points from the space. 
	 * In the end, the space will be empty.
	 */
	@Override
	public void clearAll() {
		PlaneServiceImpl.points.clear();
		PlaneServiceImpl.lines.clear();
	}
	
	/**
	 * The method returns all points from each line that has at least the number of points given by the parameter named as "number".
	 * @param number the minimum number of points that the line must have.
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
	 * @param number the minimum number of points that the line must have.
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
