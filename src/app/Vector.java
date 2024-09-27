package app;

/**
 * A linear translation within two-dimensional space.
 * This class is immutable.
 */
public class Vector {
	private final double deltax, deltay;
	
	/**
	 * Instantiates a new zero vector (no change).
	 */
	public Vector() {
		this.deltax = 0.0;
		this.deltay = 0.0;
	}
	
	/**
	 * Instantiates a new vector with specified delta values.
	 */
	public Vector(double dx, double dy) {
		this.deltax = dx;
		this.deltay = dy;
	}
	
	/**
	 * Instantiates a new unit vector with specified angle.
	 */
	public Vector(double theta) {
		this.deltax = Math.cos(theta);
		this.deltay = Math.sin(theta);
	}
	
	/**
	 * Instantiates a new vector between two points.
	 * If applied to the first point, the result is the second point.
	 */
	public Vector(Point p, Point q) {
		this.deltax = q.x() - p.x();
		this.deltay = q.y() - p.y();
	}
	
	/**
	 * Getter for deltax field.
	 */
	public double dx() {
		return deltax;
	}
	
	/**
	 * Getter for deltay field
	 */
	public double dy() {
		return deltay;
	}
	
	/**
	 * Translates the parameter point by this vector.
	 */
	public Point move(Point p) {
		return new Point(p.x() + deltax, p.y() + deltay);
	}
	
	/**
	 * Adds the parameter vector with this vector.
	 */
	public Vector add(Vector v) {
		return new Vector(this.deltax + v.deltax, this.deltay + v.deltay);
	}
	
	/**
	 * Takes the dot product of this vector and the parameter vector.
	 */
	public double dot(Vector v) {
		return this.deltax * v.deltax + this.deltay * v.deltay;
	}
	
	/**
	 * Scales this vector by the parameter.
	 */
	public Vector scale(double s) {
		return new Vector(this.deltax * s, this.deltay * s);
	}
	
	/**
	 * Finds the magnitude of this vector.
	 */
	public double magnitude() {
		return Math.sqrt(deltax * deltax + deltay * deltay);
	}
	
	/**
	 * Normalize this Vector.
	 */
	public Vector normalize() {
		double magnitude = magnitude();
		return magnitude == 0 ? new Vector() : new Vector(this.deltax / magnitude, this.deltay / magnitude);
	}
	
	/**
	 * Rotates this vector clockwise by the parameter angle (in radians).
	 */
	public Vector rotate(double theta) {
		double currentAngle = angle();
		Vector unitRotatedVector = new Vector(currentAngle + theta);
		return unitRotatedVector.scale(magnitude());
	}
	
	@Override
	public String toString() {
		return "<" + deltax + ", " + deltay + ">";
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Vector other = (Vector) o;
		return this.deltax == other.deltax && this.deltay == other.deltay;
	}
	
	@Override
	public int hashCode() {
		int hashDx = (int) dx();
		int hashDy = (int) dy();
		return 11 * hashDx + 19 * hashDy;
	}
	
	/**
	 * Compute the angle of this vector, in radians,
	 * clockwise from the x-axis.
	 */
	public double angle() {
		double magn = magnitude();
		
		if (magn == 0.0) return 0;
		
		double alpha = Math.acos(dx() / magn);
		
		if (dy() < 0) alpha = 2 * Math.PI - alpha;
		
		return alpha; // in range [0 - 2PI]
	}
}
