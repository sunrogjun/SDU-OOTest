package solitaire.pile;

import solitaire.card.Card;



public class DiscardPile extends CardPile {
	
	public DiscardPile (int x, int y) { 

		super (x, y);
		this.setType(DISCARD_PILE);
		}

	public void addCard (Object card){
		Card cards = (Card)card;
		if (!(cards.isFaceup()))
			cards.setFaceup(true);
		thePile.push(cards);
	}
  
}


