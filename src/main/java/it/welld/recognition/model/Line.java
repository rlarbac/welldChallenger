package it.welld.recognition.model;

import java.util.Set;
import java.util.TreeSet;

/**
 * This class defines a line object.
 * In order to do it, defines its state with gradient, constant and type.
 * @author Rodrigo
 *
 */
public class Line implements Comparable<Line> {
	
	/**
	 * A constructor for a line that can have an increasing trend, declining trend, vertical or horizontal.
	 * @param gradient
	 * @param constant
	 */
	private Line(double gradient, double constant) {
		
		this.gradient = gradient;
		this.constant = constant;
		
		if (gradient == 0.0) {
			this.type = LineType.HORIZONTAL;
			
		} else if (Double.valueOf(gradient).isNaN()) {
			this.type = LineType.VERTICAL;
			
		} else if (gradient < 0.0){
			this.type = LineType.DOWNWARD;
		
		} else {
			this.type = LineType.UPWARD;
		}
	}
	
	/**
	 * This is a static method that has the responsibility to create an object Line with the point a and b.
	 * In the end,it creates an object line with the gradient, constant and a line type using the straight line equation (Y = MX + C).  
	 * @param a first point.
	 * @param b second point.
	 * @return an object Line.
	 */
	public static Line newInstance(Point a, Point b) {
		
		if (a.equals(b)) {
			throw new IllegalStateException("Point a and b cannot be the same!");
		}

		// Equation of a Straight Line
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
			
		} // Vertical line
		  else if (x == 0.0) {
			m = Double.NaN;  
			c = a.getX();
			
		} else {
			// Diagonal line
			m = y / x;
			c = -m*a.getX() + a.getY(); // c = -mx + y
		}

		line = new Line(m, c);
		line.getPoints().add(a);
		line.getPoints().add(b);
		
		if (line.getPoints().size() == 1) {
			System.out.println("Point A: x: " + a.getX() + " y: " + a.getY() + ", Point B: x: " + b.getX() + " y: " + b.getY());
		}
		
		return line;
	}
	
	/**
	 * This enumeration describes the type of line.
	 * It can be: HORIZONTAL (gradient is zero), VERTICAL (gradient is not a number), UPWARD (gradient is positive) or DOWNWARD (gradient is negative).
	 * @author Rodrigo
	 *
	 */
	public enum LineType {
		HORIZONTAL,
		VERTICAL,
		UPWARD,
		DOWNWARD;
		
	}
	
	private double gradient;
	private double constant;
	private LineType type;
	private Set<Point> points = new TreeSet<Point>(); 
	
	public LineType getType() {
		return this.type;
	}
	
	public double getGradient() {
		return gradient;
	}

	public double getConstant() {
		return constant;
	}
	
	public Set<Point> getPoints() {
		return this.points;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(constant);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(gradient);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
     * One line is the same as another, only if it has 
     * the same straight-line equation (gradient and constant).
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Line other = (Line) obj;
		if (Double.doubleToLongBits(constant) != Double.doubleToLongBits(other.constant))
			return false;
		
		if (Double.doubleToLongBits(gradient) != Double.doubleToLongBits(other.gradient))
			return false;
		
		return true;
	}

	/**
     * This method is used by some kind of Set collections in order
     * to sorted the items inside the collection.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public int compareTo(Line other) {
		
		if (Double.isNaN(this.gradient) && !Double.isNaN(other.gradient)) {
			return -1;
			
		} else if (!Double.isNaN(this.gradient) && Double.isNaN(this.gradient)) {
			return 1;
		
		} else if (Double.isNaN(this.gradient) && Double.isNaN(this.gradient)) {
			
			return Double.valueOf(this.constant).compareTo(other.constant);
			
		} else if (this.gradient > other.gradient) {
			return 1;
			
		} else if (this.gradient < other.gradient) {
			return -1;
		}
		
		return Double.valueOf(this.constant).compareTo(other.constant);	}
		
}
