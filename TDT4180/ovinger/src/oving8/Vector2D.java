package oving8;

public class Vector2D {

	public double X;
	public double Y;
	public Vector2D(double x, double y) {
		X = x;
		Y = y;
	}
	
	public void Add(double x, double y) {
		X += x;
		Y += y;
	}
	
	public void Add(Vector2D amount) {
		X += amount.X;
		Y += amount.Y;
	}
	
	public void Mult(double amount) {
		X *= amount;
		Y *= amount;
	}

	public void makeUnitVector() {
		double l = length();
		X /= l;
		Y /= l;
	}
	
	public double length() {
		return Math.sqrt(Math.pow(X, 2) + Math.pow(Y, 2));
	}
}
