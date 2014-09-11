package oving5.card;

public class Card {

	private String suit;
	private int face;
	
	public Card(String suit, int face) {
		setSuit(suit);
		setFace(face);
	}
	
	private void setSuit(String suit) {
		if (suit.equals("S") || suit.equals("H") || suit.equals("D") || suit.equals("C")) {
			this.suit = suit;
		} else {
			suit = null;
		}
	}
	
	private void setFace(int face) {
		if (face >= 1 && face <= 13) {
			this.face = face;
		} else {
			this.face = -1;
		}
	}
	
	public String getSuit() {
		return suit;
	}
	
	public int getFace() {
		return face;
	}
	
	public String toString() {
		return suit + face;
	}	
}
