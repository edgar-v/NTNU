package oving5.card;

import java.util.ArrayList;

public class CardDeck {
	
	ArrayList<Card> cards;
	int cardsDrawn = 0;
	
	public CardDeck() {
		cards = new ArrayList<Card>();
		String[] suits = { "S", "H", "D", "C" };
		
		for (int i = 0; i < suits.length; i++) {
			for (int j = 1; j < 14; j++) {
				cards.add(new Card(suits[i], j));
			}
		}
	}
	
	public int getCardCount() {
		return cards.size() - cardsDrawn;
	}
	
	public Card getCard(int index) {
		if (index >= 0 && index < getCardCount()) {
			return cards.get(index);
		} else {
			return null;
		}
	}
	
	public ArrayList<Card> deal(int numCards) {
		ArrayList<Card> cardDrawn = new ArrayList<Card>();
		if (numCards > getCardCount()) {
			return null;
		}
		
		for (int i = 0; i < numCards; i++) {
			cardDrawn.add(cards.get(cards.size() - 1 - i - cardsDrawn));
		}
		
		cardsDrawn += numCards;
		
		return cardDrawn;
	}
}
