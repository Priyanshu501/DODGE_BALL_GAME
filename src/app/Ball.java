package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Vector;

/**
 * A class of two-dimensional moving disks (filled-in circles).
 * Each ball has a color used when rendering it.
 * A ball can be "active" which means it moves in the direction of its movement vector.
 * Inactive balls simply rotate every step of execution.
 */
public class Ball implements Cloneable {
	// Default radius for Ball objects.
	public static final int DEFAULT_RADIUS = 15;
	private int radius;
	private final Color color;
	private volatile boolean active;
	private Point location;
	private Vector move;
	
	/**
	 * Instantiates a new ball with specified properties.
	 */
	public Ball(Point loc, Vector vector, Color col) {
		location = loc;
		radius = DEFAULT_RADIUS;
		move = vector;
		color = col;
		active = false;
	}
	
	/**
	 * Gets the location.
	 */
	public Point getLoc() {
		return location;
	}
	
	/**
	 * Gets the radius
	 */
	public int getRadius() {
		return radius;
	}
	
	/**
	 * Return the current movement of the ball.
	 */
	public Vector getMove() {
		return move;
	}
	
	/**
	 * Set the radius of this ball.
	 */
	public void setRadius(int r) {
		if (r <= 0) {
			throw new IllegalArgumentException("radius must be positive, not " + r);
		}
		radius = r;
	}
	
	/**
	 * Launches the ball by setting active to true.
	 */
	public void launch() {
		active = true;
	}
	
	/**
	 * If ball is active: moves location by the current movement.
	 * If ball is not active: rotates vector by Math.PI/24.
	 */
	public void step() {
		if (active) {
			location = move.move(location);
		} else {
			move = move.rotate(Math.PI / 24);
		}
	}
	
	/**
	 * Checks if ball is at or outside BOUNDS dimension and moving further out,
	 * and reflects movement if so.
	 * If the ball isn't moving further out, the movement isn't reflected.
	 */
	public void bounceWalls(Dimension bounds) {
		double minX = radius;
		double minY = radius;
		double maxX = bounds.getWidth() - radius;
		double maxY = bounds.getHeight() - radius;
		
		// Check collision with left or right walls
		if (location.x() < minY && move.dx() < 0 || location.x() > maxX && move.dx() > 0) {
			move = new Vector(-move.dx(), move.dy()); // Reflect the movement in the x direction
		}
		
		// Check collision with top of bottom walls
		if (location.y() < minY && move.dy() < 0 || location.y() > maxY && move.dy() > 0) {
			move = new Vector(move.dx(), -move.dy()); // Reflect the movement in the y direction
		}
 	}
	
	/**
	 * Calculates new vectors for this and the parameter ball using normal and tangent vectors,
	 * then calls step() on both until no longer colliding.
	 */
	public void bounce(Ball other) {
		Vector my_unit_normal = new Vector(location, other.getLoc()).normalize();
		Vector other_unit_normal = new Vector(other.getLoc(), location).normalize();
		Vector my_normal = my_unit_normal.scale(move.dot(my_unit_normal));
		Vector other_normal = other_unit_normal.scale(other.move.dot(other_unit_normal));
		Vector my_tangent = move.add(my_normal.scale(-1));
		Vector other_tangent = other.move.add(other_normal.scale(-1));
		
		move = my_tangent.add(other_normal);
		other.move = other_tangent.add(my_normal);
		
		while (isColliding(other)) {
			step();
			other.step();
		}
	}
	
	/**
	 * Draws the ball, and if non-active also draws its vector.
	 */
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(location.intX() - radius, location.intY() - radius, radius * 2, radius * 2);
		if (!active) {
			Vector standard_vec = move.normalize().scale(radius * 2);
			g.setColor(Color.RED);
			g.drawLine(location.intX(), location.intY(), standard_vec.move(location).intX(), standard_vec.move(location).intY());
		}
	}
	
	/**
	 * Make a Ball with the same features as this ball.
	 */
	@Override
	public Ball clone() throws CloneNotSupportedException {
		return (Ball) super.clone();
	}
}