
public class Vector {
	private double x;
	private double y;
	
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void add(Vector v) {
		this.x += v.getX();
		this.y += v.getY();
	}
	
	public void timeStepAdd(Vector v, double timeStep) {
		this.x = this.x + (timeStep * v.getX());
		this.y = this.y + (timeStep * v.getY());
	}
	
	public void subtract(Vector v) {
		this.x = this.x - v.getX();
		this.y = this.y - v.getY();
	}
	
	public void multiply(double n) {
		this.x = this.x * n;
		this.y = this.y * n;
	}
	
	public void divide(double n) {
		this.x = this.x / n;
		this.y = this.y / n;
	}
	
	public double magnitude() {
		return Math.sqrt(x*x + y*y);
	}
	
	//To be made into a unit vector keeping direction
	public void normalize() {
		double magnitude = magnitude();
		if(magnitude != 0) {
			divide(magnitude);
		}
	}
	
	// Set magnitude to max
	public void limit(double max) {
		if(magnitude() > max) {
			normalize();
			multiply(max);
		}
	}
	
	// Add two vectors
	public static Vector add(Vector v1, Vector v2) {
		Vector v3 = new Vector(v1.getX() + v2.getX(), v1.getY() + v2.getY());
		return v3;
	}
	
	// Subtract two vectors
	public static Vector subtract(Vector v1, Vector v2) {
		Vector v3 = new Vector(v1.getX() - v2.getX(), v1.getY() - v2.getY());
		return v3;
	}
	
	public static Vector divide(Vector v1, double n) {
		Vector v2 = new Vector(v1.getX(), v1.getY());
		v2.divide(n);
		return v2;
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public static double distance(Vector v1, Vector v2) {
		return Math.sqrt(Math.pow((v1.getX() - v2.getX()),2) + Math.pow((v1.getY() - v2.getY()),2));
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	
}
