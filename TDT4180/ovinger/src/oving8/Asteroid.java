package oving8;

public class Asteroid extends SpaceObject {
	
	public Asteroid(double density, double radius, int nCorners) {
		
		for (int i = 0; i < nCorners; i++) {
			addPolarEdge(radius, (360 / nCorners) * i);
		}
		
		double area = (Math.pow(radius, 2) * nCorners * Math.sin((Math.PI * 2)/nCorners)) / 2;
		setMass(density * area);
	}
}
