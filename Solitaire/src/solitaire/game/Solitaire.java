package solitaire.game;

import jdk.nashorn.internal.scripts.JO;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//操作控制
public class Solitaire extends JPanel implements MouseListener, ActionListener,MouseMotionListener {

	private boolean isDrag = false;
	private int x;//mouse
	private int y;//mouse
    PopupMenu menu;


	public Solitaire() {
		setSize(1366, 700);
		setLayout(null);
		addMouseListener (this);
		addMouseMotionListener(this);

        menu=new PopupMenu();
        MenuItem item=new MenuItem("undo");
        MenuItem item2=new MenuItem("New game");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.undo();
                repaint();
            }
        });

        item2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Game.init();
				repaint();

			}
		});
        menu.add(item);
        menu.add(item2);
        add(menu);
	}

	@Override
	protected void paintComponent(Graphics g) {

		  g.clearRect(0, 0, 1366, 768);
		  g.setColor(new Color(0x01814A));
		  g.fillRect(0, 0, 1366, 768);
		for (int i = 0; i < 13; i++)
			Game.allPiles[i].display(g);
		Game.moveCard.display(g, x, y);
	}

	@Override
	public void actionPerformed(ActionEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {
	    this_mousePressed(e);

    }

    void this_mousePressed(MouseEvent e) {
        int mods=e.getModifiers();
//鼠标右键
        if((mods&InputEvent.BUTTON3_MASK)!=0){
//弹出菜单
            menu.show(this,e.getX(),e.getY());
        }
    }

    @Override
	public void mousePressed(MouseEvent e) {

		 x = e.getX();
		 y = e.getY();
		 isDrag = false;
	     boolean isSelect = false;
		 isSelect = Game.testDeckPile(x,y);
		 if(!isSelect){
			 isSelect = Game.testDisCardPile(x, y);
			    if(isSelect){
//			       System.out.println("选中弃牌");
				   isDrag = true;
			    }
		        if(!isSelect){
			    isSelect = Game.testTablePile(x, y);
			    if(isSelect) {
//			        System.out.println("选中桌面");
                    isDrag = true;
                    }
                    else{
			        Game.testSuitPile(x,y);
//			        System.out.println("选中结果");
			        isDrag=true;
                }
			    }

		 }
		 isDrag = false;
		 repaint();

	   }

	@Override
	public void mouseReleased(MouseEvent e) {



        if(isDrag &&  Game.moveCard.size() > 0 ){
        	boolean isCanAdd =false;
        	isCanAdd = Game.isCanAddToSuitPile(x,y);
        	if(!isCanAdd)
        		isCanAdd = Game.isCanAddtoTablePile(x, y);

        	if(!isCanAdd ){
        		Game.usedPile.pop();
        	    Game.returnToFromPile();
        	}
        	else
        		Game.refreshTablePile();
        	isDrag = false;
        	repaint();
        }
        else{
        	if(Game.moveCard.size() > 0) {
				Game.usedPile.pop();//清除无效记录
        	    Game.returnToFromPile();

			}
        repaint();
			if (Game.isWin()){
//            int exi = JOptionPane.showConfirmDialog (null, "You  Win !", null, JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE);
//            if (exi == JOptionPane.YES_OPTION)
//            {
//                System.exit (0);
//            }
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null,"You win !","",JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}

        }
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e){}

	@Override
	public void mouseDragged(MouseEvent e){

		isDrag = true;
		x = e.getX();
		y = e.getY();
		repaint();

	}

	@Override
	public void mouseMoved(MouseEvent e) {}


}
