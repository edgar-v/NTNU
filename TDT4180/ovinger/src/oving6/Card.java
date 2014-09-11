package oving6;

public class Card implements java.lang.Comparable<Card> {
	
	private int face;
	private String suit;
	
	public Card(String suit, int face) {
		if (suit == "S" || suit == "H" || suit == "D" || suit == "C") {
			this.suit = suit;
		}
		if (face >= 1 && face <= 13) {
			this.face = face;
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
	
	public int compareTo(Card card) {
		String suits = "SHDC";
		if (((Card)card).face == this.face) {
			if (card.suit == this.suit) {
				return 0;
			} else if (suits.indexOf(card.suit) > suits.indexOf(this.suit)) {
				return 1;
			} else {
				return -1;
			}
		} else if (this.face == 1) {
			return 1;
		} else if (card.face == 1) {
			return -1;
		} else {
			return this.face - card.face;
		}
	}

}
