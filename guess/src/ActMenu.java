import org.jetbrains.annotations.NotNull;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import javax.swing.*;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
public class ActMenu extends JPanel{
    JButton buttons[] = {new JButton("新用户"),new JButton("猜新数"),new JButton("排名")};

    public ActMenu() {
        Font font=new Font("楷体",Font.BOLD,20);//设置字体
        buttons[0].setFont(font);//设置按钮字体
        buttons[1].setFont(font);//设置按钮字体
        buttons[2].setFont(font);//设置按钮字体
        setLayout(new FlowLayout());

        for (int index = 0; index < buttons.length; index++)
            buttons[index].addActionListener(new ActLis());

        for (int index = 0; index < buttons.length; index++)
            add(buttons[index]);
    }

    public void shutdown() {
        for (int index = 0; index < run.frames.length; index++)
            run.frames[index].setVisible(false);
    }

    private class ActLis implements ActionListener {

        public void actionPerformed(@NotNull ActionEvent e) {
            if (e.getSource() == buttons[0]) {
                shutdown();
                if(Game.count>=1&&Game.users[Game.count - 2].name == null) Game.count--;
                run.frames[1].setVisible(true);
                new Login();
                Game.temp=-1;
                run.game.greet.setText(Client.greetings(Game.count));
                resetuser();
                run.game.process();
            }
            else if (e.getSource() == buttons[1]) {
                shutdown();
                if(Game.count>=1&&Game.users[Game.count - 2].name == null) Game.count--;
                run.frames[1].setVisible(true);
                resetnum();
                run.game.process();
            }
            else if (e.getSource() == buttons[2]) {
                shutdown();
                if(Game.count>=1&&Game.users[Game.count - 2].name == null) Game.count--;
                run.frames[2].setVisible(true);
                run.rank.d.setText(run.rank.printRank());
            }
        }
    }

    public void resetnum(){
        run.game.guess.setText("");
        run.game.scores.setText("");
        run.game.note.setText("");
        run.game.str="";
    }

    public void resetuser(){
        run.game.nam.setText("");
        run.game.guess.setText("");
        run.game.scores.setText("");
        run.game.note.setText("");
        run.game.str="";
    }
}
