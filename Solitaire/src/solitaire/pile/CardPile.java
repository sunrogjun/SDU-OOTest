package solitaire.pile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Stack;
import java.util.EmptyStackException;

import javax.imageio.ImageIO;

import solitaire.card.Card;

public class CardPile {//牌堆超类

    public static final int TABLE_PILE=1;
    public static final int SUIT_PILE=2;
    public static final int DECK_PILE=3;
    public static final int DISCARD_PILE=4;
    public int type;
    public int x;
    public int y;
    public Stack<Card> thePile;

    public CardPile(int xl, int yl) {
        x = xl;
        y = yl;
        thePile = new Stack<Card>();

    }

    public void setType(int type){
        this.type=type;
    }

    public int getType(){
        return this.type;
    }

    public Card top() { //牌堆的第一张牌
        if (!(thePile.empty()))
            return (Card) thePile.peek();
        else
            return null;
    }

    public boolean isEmpty() {
        return thePile.empty();
    }

    public Card pop() {
        try {
            return (Card) thePile.pop();

        } catch (EmptyStackException e) {
            return null;
        }
    }

    public Card peek(){
        if (thePile.empty())return null;
        return (Card)thePile.peek();
    }


    public boolean includes(int tx, int ty) { //判断位置是否在合适的区域内
        return this.x <= tx && tx <= this.x + Card.width &&
                this.y <= ty && ty <= this.y + Card.height;
    }

    public boolean isSame(CardPile pile){//判断两个牌堆是否同一个牌堆
        return this.x<=pile.x&&pile.x<=this.x+Card.width&&this.y<=pile.y&&pile.y<=this.y+Card.height;
    }

    public int select(int tx, int ty) {
        if (includes(tx, ty)) {
            if (isEmpty())
                return -2;
            else
                return thePile.size() - 1;
        } else
            return -1;
    }

    public void addCard(Object card) {
        thePile.push((Card) card);
    }

    public void addSingleCard(Object card){

    }

    public boolean isCanAdd(Card card) {
        return false;
    }

    public void display(Graphics g) {

        if (isEmpty()) {
            Image image = null;
            try {
                String picture = "poker/0.png";
                image = ImageIO.read(new File(picture));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("class-CardPile-display-if(isEmpty())");
            }
            g.drawImage(image, this.x, this.y, Card.width, Card.height, null);
        } else {
if(top()==null){
    System.out.println( this.getClass().toString());
    System.out.println(isEmpty());
}
                top().setX(x);
                top().setY(y);
                top().draw(g);

        }
    }

}

