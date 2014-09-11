package oving8;

import java.awt.Color;

public class Spaceship extends SpaceObject {
	
	private int rotation;
	
	public Spaceship() {
		setMass(10.0);
		addPolarEdge(50, 0);
		addPolarEdge(50, 135);
		addPolarEdge(50, 225);
		setFilled(true);
		setFillColor(Color.WHITE);
		rotation = 0;
	}
	
	public void setDirection(int angle) {
		rotation = angle;
		rotate(angle);
	}
	
	public void incrementDirection(int amount) {
		rotation += amount;
		rotate(amount);
	}
	
	public void thrust() {
		accelerate(Math.cos(rotation), Math.sin(rotation));
	}
}
