package solitaire.pile;


public class UsedPile extends CardPile {

    public int nowX;
    public int nowY;
    public boolean isup=false;//撤销时最后一张牌是否朝上


    public boolean isIsup() {
        return isup;
    }

    public void setIsup(boolean isup) {
        this.isup = isup;
    }


    public CardPile lastPile;//上一个牌堆

    public CardPile usedPile;//现在的牌堆

    public void setUsedPile(CardPile usedPile,int nx,int ny) {
        this.usedPile = usedPile;
        nowX=nx;
        nowY=ny;
    }
    public UsedPile(int x,int y){
        super(x,y);
        type=0;
    }
    public UsedPile(int x,int y,CardPile lastpile){ //x,y是lastpile的位置
        super(x,y);
        type=0;
        lastPile=lastpile;
    }

    public void setLastPile(CardPile lastPile) {
        this.lastPile = lastPile;
    }
}
