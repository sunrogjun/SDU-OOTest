package solitaire.game;

import javax.swing.*;

public class Main extends JFrame{
    private Solitaire sp;
	public Main(){
		setSize(1366, 735);
		setTitle("Solitaire Game");
	    setLayout(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);

		sp = new Solitaire();
		add(sp);
	}
	public static void main(String[] args) {
		Main main = new Main();
	}

}
