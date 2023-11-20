package solitaire.pile;


import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.imageio.ImageIO;

import solitaire.card.Card;

public class TablePile extends CardPile {
    private int notFlipNum;
    private int  cardNum;
    final public static int separation  = 30;
    final public static int unFlipCardSeparation = 10;
	 public TablePile(int x, int y,int notFlipNum){
		super(x, y);
		this.notFlipNum = notFlipNum;
		cardNum = notFlipNum+1;
		this.setType(TABLE_PILE);
		
	}

	public TablePile(int x,int y){
	     super(x,y);
	     cardNum=0;
	     this.notFlipNum=0;
	     this.setType(TABLE_PILE);
    }

	@Override
	public boolean includes(int tx, int ty) {
		int beginX,beginY,endX,endY;
		beginX = x;
		beginY = y ;
		endX = x + Card.width;
		if(thePile.size() > 0)
		  endY =  beginY  + unFlipCardSeparation * notFlipNum + separation * (thePile.size() - 1 - notFlipNum) + Card.height ;
		else
		  endY =  beginY  + Card.height;
		boolean isInclude =  beginX <= tx && tx <= endX && beginY <= ty && ty <= endY;
		return isInclude;
	}

	@Override
	public int select(int tx, int ty) {
		if(!(isEmpty())){
		int beginX,beginY,endX,endY;
		//System.out.println(notFlipNum+"	"+cardNum);
		beginX = x  ;
		beginY = y + unFlipCardSeparation * notFlipNum;
		endX = x + Card.width;
		endY =  beginY  + unFlipCardSeparation * notFlipNum + separation * (thePile.size() - 1 - notFlipNum) + Card.height;
		boolean flip_include =  beginX <= tx && tx <= endX && beginY <= ty && ty <= endY;
		//System.out.println(beginY+"	"+endY);
		if(flip_include){
		 
		 int c = (ty - beginY)/separation + notFlipNum;
		 if(c >= thePile.size()){
			c =  thePile.size() - 1;
		 }
		 return c;//
		}
		else
			return -1;
		}
		else
			return -1;
	}

	@Override
	public void addCard(Object card) {

		ArrayList<Card> cardList = (ArrayList<Card>)card;//todo Exception in thread "AWT-EventQueue-0" java.lang.ClassCastException: solitaire.card.Card cannot be cast to java.util.ArrayList
		cardNum += cardList.size();
		for(int i = 0;i < cardList.size();i++){
			thePile.push(cardList.get(i));
		}
	}

	@Override
	public void addSingleCard(Object card){
	 	Card card1=(Card)card;
	 	cardNum++;
	 	thePile.push(card1);
	}

	@Override
	public Card pop() {

		cardNum--;
		return super.pop();
	}
	@Override
	public boolean isCanAdd(Card card){

		if ( isEmpty())
			return card.getNum() == 12;
		Card topCard = top();
		return (card.getColor() != topCard.getColor()) &&
			(card.getNum() ==  topCard.getNum()-1 );
	}

	@Override
	public void display(Graphics g) {
		if (isEmpty()){
			Image image= null;
			 try {   
//			     String picture = "picture/0.png";
				 String picture = "poker/0.png";
				 image = ImageIO.read(new   File(picture)); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("class-CardPile-display-if(isEmpty())");
			} 
			g.drawImage(image, this.x,this.y, Card.width, Card.height, null);
		}
		else{
		int localy = y;
		for (Enumeration e = thePile.elements(); e.hasMoreElements(); ) {
			
			Card aCard = (Card) e.nextElement();
			aCard.setX(x);
			aCard.setY(localy);
			aCard.draw(g);
			if(aCard.isFaceup())
			   localy += separation;
			else
				localy += unFlipCardSeparation;
			
		}
		}
	}
	public int getNotFlipNum() {
		return notFlipNum;
	}
	public void setNotFlipNum(int notFlipNum) {
		this.notFlipNum = notFlipNum;
	}
	public int getCardNum() {
		return cardNum;
	}
	public void setCardNum(int cardNum) {
		this.cardNum = cardNum;
	}
	public static int getSeparation() {
		return separation;
	}
	public static int getUnflipcardseparation() {
		return unFlipCardSeparation;
	}

	
}