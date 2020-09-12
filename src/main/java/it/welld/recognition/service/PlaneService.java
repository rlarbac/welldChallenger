package it.welld.recognition.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import it.welld.recognition.model.Line;
import it.welld.recognition.model.Point;

public interface PlaneService {

	/**
	 * This method receives a new point and adds to the space.
	 * If the new point already in the space, it returns false (in other words, nothing happened),
	 * otherwise, it returns true (the point was added to the space)
	 * @param newPoint
	 * @return boolean 
	 */
	public boolean addPoint(Point newPoint);
	
	/**
	 * The method returns all lines (with their properties - gradient, constant and type)
	 * that have at least the number of points given by the parameter named as "number".
	 * @param number the maximum number of points that the line must have.
	 * @return list It returns a list of lines.
	 */ 
	public List<Line> getLinesByNumberOfPoints(int number);
	
	/**
	 * The method removes all the points from the space. In the end, the space will be empty.
	 */ 
	public void clearAll();
	
	/**
	 * The method returns a list of all points from the space.
	 * @return a list of all points from the space.
	 */
	public Collection<Point> getPoints();
	
	/**
	 * The method returns all points from each line that has at least the number of points given by the parameter named as "number".
	 * @param number the maximum number of points that the line must have.
	 * @return list It returns a list of set of points from each line.
	 */ 
	public List<Set<Point>> getAllPointsFromEachLineByNumberOfPoints(int number);	
}
