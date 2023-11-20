package solitaire.card;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
//卡牌的配置，包括图案花色数字等
public class Card {



	final public static int width = 96;
	final public static int height = 130;

//四种花色
final public static int heart = 0;
final public static int spade = 1;
final public static int diamond = 2;
final public static int club = 3;

private boolean faceup; //正反面
private int num;
private int type;
private int x;
private int y;

public Card (int num, int type) {
	this.num = num;
	this.type = type;
	this.faceup = false;
	}
public Color getColor() {
	if (isFaceup()){
		if (getType() == heart || getType() == diamond)
			return Color.red;
		else
			return Color.black;
	}
	return Color.yellow;
	}
public void draw (Graphics g) {


	Image image= null;
	if (isFaceup()) {
		 try {
//			     String picture = "picture/"+this.type+"-"+this.num+".png";
			 	String picture = "poker/"+this.type+"-"+this.num+".jpg";
				 image = ImageIO.read(new   File(picture));
			} catch (IOException e) {

				e.printStackTrace();
				System.out.println("class-draw-if");
			}
    }
	else {
		 try {
		     String picture = "poker/back.png";
			 image = ImageIO.read(new   File(picture));
		} catch (IOException e) {

			e.printStackTrace();
			System.out.println("class-draw-else");
		}

	}
	g.drawImage(image, getX(), getY(), Card.width, Card.height, null);
  }
public boolean isFaceup() {
	return faceup;
}
public void setFaceup(boolean faceup) {
	this.faceup = faceup;
}
public int getNum() {
	return num;
}
public void setNum(int num) {
	this.num = num;
}
public int getType() {
	return type;
}
public void setType(int type) {
	this.type = type;
}
public int getX() {
	return x;
}
public void setX(int x) {
	this.x = x;
}
public int getY() {
	return y;
}
public void setY(int y) {
	this.y = y;
}


}
