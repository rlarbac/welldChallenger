package it.welld.recognition.service;

import java.util.Collection;
import java.util.List;

import it.welld.recognition.model.Line;
import it.welld.recognition.model.Point;

public interface PlaneService {

	public void addPoint(Point newPoint);
	public List<Line> getLinesByNumberOfPoints(int number);
	public void clearAll();
	public Collection<Point> getPoints();
}
