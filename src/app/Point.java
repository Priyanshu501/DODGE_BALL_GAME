package app;

/**
 * A point in two-dimensional Euclidean space.
 * This class is immutable.
 */
public class Point implements Cloneable {
	private final double x, y;
	
	// The constructor
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	// Returns the first field
	public double x() {
		return x;
	}
	
	// Returns the second field
	public double y() {
		return y;
	}
	
	// Returns the first field rounded to the nearest integer
	public int intX() {
		return (int) Math.round(x);
	}
	
	// Returns the second field rounded to the nearest integer
	public int intY() {
		return (int) Math.round(y);
	}
	
	/**
	 * Returns the distance to the other point from here
	 * by computing the magnitude of the vector between them.
	 */
	public double distance(Point other) {
		double dx = this.x - other.x;
		double dy = this.y - other.y;
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	/**
	 * Returns 'true' if the other point's coordinates are exactly the same.
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) return true;
		
		if (obj == null || getClass() != obj.getClass()) return false;
		
		Point other = (Point) obj;
		
		return Double.compare(other.x, x) == 0 && Double.compare(other.y, y) == 0;
	}
	
	/**
	 * Returns an integer associated with this Point.
	 * Using formula 7x + 23y
	 */
	@Override
	public int hashCode() {
		int hashX = (int) x();
		int hashY = (int) y();
		
		return 7 * hashX + 23 * hashY;
	}
	
	/**
	 * Returns a string of the form '(x, y)',
	 * using Java's default way to encode double precision
	 * floating point values as strings.
	 */
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}