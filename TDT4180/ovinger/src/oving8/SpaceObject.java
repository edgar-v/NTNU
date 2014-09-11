package oving8;

import java.awt.Point;

import acm.graphics.GPolygon;
import acm.graphics.GRectangle;

public class SpaceObject extends GPolygon {
	
	final double GravitationalConstant = 0.01;
	
	private Vector2D speed = new Vector2D(0, 0);
	private double mass = 0;
	
	public double getSpeedX() {
		return speed.X;
	}
	
	public double getSpeedY() {
		return speed.Y;
	}
	
	public Vector2D getSpeed() {
		return speed;
	}
	
	public void setSpeed(double x, double y) {
		speed.X = x;
		speed.Y = y;
	}
	
	public void setSpeed(Vector2D speed) {
		this.speed = speed;
	}
	
	public void accelerate(double x, double y) {
		speed.Add(x, y);
	}
	
	public void accelerate(Vector2D amount) {
		speed.Add(amount);
	}
	
	public double getMass() {
		return mass;
	}
	
	public void setMass(double amount) {
		mass = amount;
	}
	
	public void tick() {
		move(speed.X, speed.Y);
	}
	
	public boolean intersects(SpaceObject obj) {
		GRectangle rect1 = obj.getBounds();
		GRectangle rect2 = this.getBounds();
		
		return (rect1.intersects(rect2));
		
	}
	
	public void applyGravitationalForce(SpaceObject object) {
		if (mass == 0) {
			return;
		}
		double x1 = this.getX();
		double y1 = this.getY();
		double x2 = object.getX();
		double y2 = object.getY();
		double r = Math.sqrt(((x2-x1)*(x2-x1)) + ((y2-y1)*(y2-y1)));
		Vector2D direction = new Vector2D(x2 - x1, y2 - y1);
		direction.makeUnitVector();
		double force = (GravitationalConstant * this.getMass() * object.getMass()) / Math.pow(r, 2);
		double acc = force / mass;
		direction.Mult(acc);
		if (mass < 1000) {
			speed.Add(direction);
		}
	}
}
