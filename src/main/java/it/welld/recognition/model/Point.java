package it.welld.recognition.model;

public class Point implements Comparable<Point> {

	private double x;
	private double y;
	
	public Point () {
	
	}
	
	public Point (double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

	@Override
	public int compareTo(Point other) {
		
		if (this.x > other.x) {
			return 1;
		} else if (this.x < other.x) {
			return -1;
		} else if (this.y > other.y) {
			return 1;
		} else if (this.y > other.y) {
			return -1;
		}
		return 0;
	}
	
	
}
