package solitaire.pile;

import solitaire.card.Card;

import java.util.ArrayList;


public class SuitPile extends CardPile {

	public SuitPile (int x, int y) {
	    super(x, y);
	    this.setType(SUIT_PILE);
	}

	@Override
	public boolean isCanAdd(Card card) {

		if (isEmpty())
			return card.getNum() == 0;
		Card topCard = top();
		return (card.getType() == topCard.getType()) &&
			(card.getNum() ==  topCard.getNum() + 1);
		
	}

	public int size(){
	    return thePile.size();
    }

    @Override
    public void addCard(Object card) {
        //solitaire.card.Card cannot be cast to java.util.ArrayList

//        ArrayList<Card> cardList = (ArrayList<Card>)card;

            thePile.push((Card)card);
    }

    public void addSingleCard(Object card){
//	    if (!thePile.empty())
//	    for (int i=0;i<thePile.size();i++){
//	        Card t=thePile.pop();
//            System.out.println(t.getNum()+"    thepile     "+t.getType());
//        }
        ArrayList<Card> cardList = (ArrayList<Card>)card;
        for (int i=0;i<cardList.size();i++){
            System.out.println(cardList.get(i).getNum()+"         "+cardList.size()+"  i:"+i+"           "+cardList.get(i).getType());
        }
        thePile.push(cardList.get(0));
    }


}