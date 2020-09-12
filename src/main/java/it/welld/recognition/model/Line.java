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
	 * A convenient constructor for a line that has an increasing or declining trend.
	 * @param gradient
	 * @param constant
	 */
	public Line(double gradient, double constant) {
		this(gradient, constant, LineType.DIAGONAL);
	}
	
	/**
	 * A constructor for line that can be vertical, horizontal or diagonal.
	 * @param gradient
	 * @param constant
	 */
	public Line(double gradient, double constant, LineType type) {
		this.gradient = gradient;
		this.constant = constant;
		this.type = type;
	}
	
	/**
	 * An enumeration that describes the type of line.
	 * It can be: HORIZONTAL (gradient is zero), VERTICAL (gradient is not a number) or DIAGONAL (if it is not zero or NaN).
	 * @author rlarb
	 *
	 */
	public enum LineType {
		HORIZONTAL,
		VERTICAL,
		DIAGONAL;
		
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
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/**
     * One line is the same as another, only if it has 
     * the same gradient, constant and type.
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
		if (type != other.type)
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
		
		if (this.gradient > other.gradient) {
			return 1;
		} else if (this.gradient < other.gradient) {
			return -1;
		} else if (this.constant > other.constant) {
			return 1;
		} else if (this.constant < other.constant) {
			return -1;
			
		} else if (!this.type.equals(other.type)) {
			return -1;
		}
		return 0;
	}
		
}
