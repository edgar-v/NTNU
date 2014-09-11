package oving3;

import acm.graphics.GImage;

public class Card {
	String suit;
	int face;
	GImage image;
	
	public String toString() {
		return suit + face;
	}
}
