package solitaire.game;


import java.util.*;

import solitaire.pile.*;
import solitaire.card.*;
//逻辑控制
public class Game {
    static public ArrayList<Card> allCard;
    static public CardPile allPiles[];
    static public DeckPile deckPile;//发牌堆
    static public DiscardPile discardPile;//弃牌堆
    static public TablePile tablePile[];//桌面上的牌堆
    static public SuitPile suitPile[]; //4个存放可以匹配的牌堆
    static public MoveCardPile moveCard;
    static public Stack<UsedPile> usedPile;//用过的牌堆，用于撤销

    static {
        init();
    }

    static void init(){
        //初始化所有牌
        allCard = new ArrayList<Card>();
        for (int i = 0; i < 4; i++)
            for (int j = 0; j <= 12; j++)
                allCard.add(new Card(j, i));
        Random generator = new Random();
        for (int i = 0; i < 52; i++) {
            int j = Math.abs(generator.nextInt() % 52);
            // 交换两张牌的值
            Card temp = allCard.get(i);
            allCard.set(i, allCard.get(j));
            allCard.set(j, temp);
        }
        //初始化各个牌堆
        allPiles = new CardPile[13];
        suitPile = new SuitPile[4];
        tablePile = new TablePile[7];

        allPiles[0] = deckPile = new DeckPile(200, 40);
        allPiles[1] = discardPile = new DiscardPile(200 + Card.width + 50, 40);
        for (int i = 0; i < 4; i++)
            allPiles[2 + i] = suitPile[i] = new SuitPile(200 + Card.width + 50 + Card.width + 150 + (40 + Card.width) * i, 40);
        for (int i = 0; i < 7; i++)
            allPiles[6 + i] = tablePile[i] = new TablePile(200 + (50 + Card.width) * i, 40 + Card.height + 40, i);
        for (int i = 0; i < 7; i++) {
            ArrayList<Card> al = new ArrayList<Card>();
            for (int j = 0; j < tablePile[i].getCardNum(); j++) {
                al.add(allCard.remove(allCard.size() - 1));
            }
            tablePile[i].addCard(al);
            tablePile[i].setCardNum(tablePile[i].getNotFlipNum() + 1);
            tablePile[i].top().setFaceup(true);
        }
        int rest = allCard.size();
        for (int i = 0; i < rest; i++) {
            deckPile.addCard(allCard.remove(allCard.size() - 1));
        }
        moveCard = new MoveCardPile();
        usedPile=new Stack<>();
    }

    public static void transferFromDiscardToDeck() { //把弃牌堆重新转化为发牌堆

        while (!(discardPile.isEmpty())) {
            Card card = discardPile.pop();
            card.setFaceup(false);
            deckPile.addCard(card);

        }
    }

    public static TablePile findTablePile(int x,int y){
        for (int i=0;i<7;i++){
            if (tablePile[i].includes(x,y))return tablePile[i];
        }
        return null;
    }

    public static SuitPile findSuitPile(int x,int y){
        for (int i=0;i<4;i++){
            if (suitPile[i].includes(x,y))return suitPile[i];
        }
        return null;
    }

    public static CardPile findLastPile(CardPile pile){
        if (pile.getType()==CardPile.TABLE_PILE){
           for (int i=0;i<7;i++){
               if (tablePile[i].isSame(pile))return tablePile[i];
           }
        }
        else if(pile.getType()==CardPile.SUIT_PILE){
            for (int i=0;i<4;i++){
                if (suitPile[i].isSame(pile))return suitPile[i];
            }
        }
        else if(pile.getType()==CardPile.DISCARD_PILE)return discardPile;
        else if (pile.getType()==CardPile.DECK_PILE)return deckPile;
        return null;
    }


    public static void undo(){ //撤销上一步，可连续
        if (usedPile.isEmpty()){
//            System.out.println("empty!!!!!!!");
            return;
        }

        UsedPile pile=usedPile.pop();
        if(pile.usedPile.getType()==CardPile.TABLE_PILE){

            TablePile tp=findTablePile(pile.nowX,pile.nowY);
            if (tp!=null){
                CardPile last=findLastPile(pile.lastPile);
                if (last!=null){
//                    System.out.println("从tablePile 撤回到"+last.getType()+"   pile.size:  "+pile.thePile.size());
                    int count=pile.thePile.size();
                    Stack<Card> stack=new Stack<>();
                    while (count>0){
                        count--;
                            Card t=tp.pop();
//                            System.out.println(t.getNum()+"       undo      "+t.getType());
                            stack.push(t);

                        }
                        if (last.getType()==CardPile.TABLE_PILE) {
                        if (!last.isEmpty()) {
                            if (((TablePile)last).getNotFlipNum()==((TablePile) last).getCardNum()-1&&!pile.isIsup()){
                                last.top().setFaceup(false);
                                ((TablePile) last).setNotFlipNum(((TablePile) last).getNotFlipNum() + 1);
                            }
                        }
                        }else if(last.getType()==CardPile.DISCARD_PILE){
                            discardPile.addCard(stack.pop());
//                            System.out.println("discard!!!!!!!!!!!!!");
                            return;
                        }


                        while (!stack.empty()){
                            last.addSingleCard(stack.pop());
//                            System.out.println(last.top().getNum()+"      top       "+last.top().getType());
                        }
                }


            }


        }
        else if(pile.usedPile.getType()==CardPile.DECK_PILE){
//            System.out.println("DeckPile!!!!");
        }
        else if(pile.usedPile.getType()==CardPile.DISCARD_PILE){//撤销发牌
            Card temp=discardPile.pop();
            if (temp!=null) {
                temp.setFaceup(false);
                deckPile.addCard(temp);
            }
        }
        else if(pile.usedPile.getType()==CardPile.SUIT_PILE){
            if (pile.lastPile.getType()==CardPile.DISCARD_PILE){
                discardPile.addCard(findSuitPile(pile.nowX,pile.nowY).pop());
            }
            else if(pile.lastPile.getType()==CardPile.TABLE_PILE){
//                System.out.println("table!!!!!!!!!");
                if(findTablePile(pile.x,pile.y).getNotFlipNum()==findTablePile(pile.x,pile.y).getCardNum()-1){
                    findTablePile(pile.x,pile.y).top().setFaceup(false);
                    findTablePile(pile.x,pile.y).setNotFlipNum(findTablePile(pile.x,pile.y).getNotFlipNum()+1);
                }
                findTablePile(pile.x,pile.y).addSingleCard(findSuitPile(pile.nowX,pile.nowY).pop());
            }
            else if(pile.lastPile.getType()==CardPile.SUIT_PILE){
                findSuitPile(pile.x,pile.y).addCard(findSuitPile(pile.nowX,pile.nowY).pop());
            }

        }

    }



    public static boolean testDeckPile(int x, int y) {
        int selectNum = deckPile.select(x, y);//未知牌堆的数量
        if (selectNum >= 0) {
            Card c=deckPile.pop();
            discardPile.addCard(c);//发牌一次

            //记录到使用牌堆

            UsedPile up=new UsedPile(x,y,deckPile);
            up.setUsedPile(discardPile,discardPile.x,discardPile.y);
            up.addCard(c);
            usedPile.push(up);

            return true;
        } else if (selectNum == -2) {
            Game.transferFromDiscardToDeck();
            return true;
        } else {
            return false;
        }
    }

    //选中结果牌堆
    public static boolean testSuitPile(int x, int y) {//todo
        for (int i = 0; i < 4; i++) {
            int selectNum = suitPile[i].select(x, y);
            if (selectNum >= 0) {
//	            System.out.println("choose  pile "+i);
                moveCard.clear();
                Card t = suitPile[i].pop();
//                System.out.println(t.getNum()+"  pop  "+t.getType());
                moveCard.addCard(t);
//                SuitPile temp=new SuitPile(x,y);
//                temp.addCard(t);
                moveCard.setFromPile(suitPile[i]);

                //记录
                UsedPile up=new UsedPile(x,y,suitPile[i]);
                up.addCard(t);
                usedPile.push(up);


                return true;
            }
        }

        return false;
    }

    public static boolean testDisCardPile(int x, int y) {
        int selectNum = discardPile.select(x, y);
        if (selectNum >= 0) {
            moveCard.clear();
            Card c=discardPile.pop();
            moveCard.addCard(c);
            moveCard.setFromPile(discardPile);

            //记录
            UsedPile up=new UsedPile(x,y,discardPile);
            up.addCard(c);
            usedPile.push(up);



            return true;
        } else if (selectNum == -2) {
        } else {
        }
        return false;
    }

    public static boolean testTablePile(int x, int y) {
        boolean isDrag = false;
        for (int i = 0; i < tablePile.length; i++) {
            int selectNum = tablePile[i].select(x, y);
            if (selectNum >= 0) {
                moveCard.clear();

                int num = tablePile[i].getCardNum();
                UsedPile up=new UsedPile(x,y,tablePile[i]);
                for (int j = selectNum; j < num; j++) {

                    Card temp=tablePile[i].pop();
                    up.addCard(temp);
                    moveCard.addCard(temp);
                }
                //
                if (selectNum+tablePile[i].getNotFlipNum()<tablePile[i].getCardNum())up.setIsup(true);//记录是否撤回时该朝上
                moveCard.setFromPile(tablePile[i]);
                usedPile.push(up);

                //System.out.println("moveCard_size:"+moveCard.size() );
                return true;
            } else {
                // System.out.println("tablePile["+i+"]_selectNum:"+-1);
            }
            //System.out.println("tablePile["+i+"]_size:"+tablePile[i].thePile.size() );
        }

        return isDrag;
    }

    public static boolean isCanAddToSuitPile(int x, int y) {
        if (moveCard.size() == 1) {
            for (int i = 0; i < 4; i++) {
                if (suitPile[i].includes(x, y)) {
                    if (suitPile[i].isCanAdd(moveCard.getCard())) {
                        suitPile[i].addCard(moveCard.removeCard());
                        usedPile.peek().setUsedPile(suitPile[i],x,y);//记录
                        return true;
                    }

                }
            }
        }

        return false;
    }

    public static boolean isCanAddtoTablePile(int x, int y) {
        for (int i = 0; i < 7; i++) {
            if (tablePile[i].includes(x, y)) {
                if (tablePile[i].hashCode() != moveCard.getFromPile().hashCode()) {
                    if (tablePile[i].isCanAdd(moveCard.getCard())) {
                        tablePile[i].addCard(moveCard.clear());
                        usedPile.peek().setUsedPile(tablePile[i],x,y);//记录
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isWin() { //判断是否胜利
        for (int i = 0; i < 4; i++) {
            if (suitPile[i].size() < 13) return false;
        }
        return true;
    }


    public static void refreshTablePile() {

        for (int i = 0; i < 7; i++) {
            if (tablePile[i].top() != null)
                if (!(tablePile[i].top().isFaceup())) {
                    tablePile[i].top().setFaceup(true);
                    tablePile[i].setNotFlipNum(tablePile[i].getNotFlipNum() - 1);
                }
        }
    }

    public static void returnToFromPile() {
        if (moveCard.getFromPile() != null)
            if (moveCard.getFromPile().hashCode() == discardPile.hashCode()) {
                while (!(moveCard.isEmpty())) {
                    moveCard.getFromPile().addCard(moveCard.removeCard());
                }
            } else {
                ArrayList<Card> temp = moveCard.clear();
//            System.out.println("else    movecard:"+moveCard.getFromPile().getClass().toString()+"   clear:  "+temp.getClass().toString());
                if (moveCard.getFromPile().getClass().equals(SuitPile.class)) {//回退结果牌堆的牌
                    Card t = temp.remove(0);
//                System.out.println(t.getNum()+"      isnull?           "+t.getType());
                    moveCard.getFromPile().addCard(t);

                } else {
                    moveCard.getFromPile().addCard(temp);//
//                    for (int i = 0; i < temp.size(); i++) {
////                    System.out.println("num:" + temp.get(i).getNum() + "  type:" + temp.get(i).getType());
//                    }
                }
            }


    }
}

