package oving3;

import java.awt.Graphics;
import java.util.ArrayList;

import acm.graphics.GImage;
import acm.program.GraphicsProgram;

public class CardDeck extends GraphicsProgram{
	
	ArrayList<Card> cards = new ArrayList<Card>();
	
	public void init() {
		String[] suits = {"S", "H", "D", "C"};
		for (int i = 0; i < 4; i++) {
			for (int j = 1; j < 14; j++) {
				Card newCard = new Card();
				newCard.suit = suits[i];
				newCard.face = j;
				newCard.image = createGImage(suits[i], j);
				cards.add(newCard);
			}
		}
	}
	
	
	public Card getCard(int index) {
		return cards.get(index);
	}
	
	public void run()
	{
		init();
		drawCards();
	}
	
	private void drawCards() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				add(cards.get(i * 13 + j).image, j * 50, i * 100);
			}
		}
	}
	
	GImage createGImage(String suit, int value) {
		String name="";
		switch(suit.charAt(0)){
		case 'H':
			name+="hearts";
			break;
		case 'D':
			name+="diamonds";
			break;
		case 'C':
			name+="clubs";
			break;
		case 'S':
			name+="spades";
			break;
		}
		name+="-";
		switch(value) {
		case 1:
			name+="a";
			break;
		case 11:
			name+="j";
			break;
		case 12:
			name+="q";
			break;
		case 13:
			name+="k";
			break;
		default:
			name+="" + value;
		}

		name+="-150.png";
		return new GImage("oving3/img/" + name);
	}
}
