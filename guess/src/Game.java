import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.*;
import javax.swing.*;


public class Game extends JFrame{
    static int length = 4;
    static int count;
    static int temp=-1;
    static int mem_count=count;
    static Client[] users=new Client[100];
    JLabel center = new JLabel();

    JLabel first=new JLabel();
    JTextField greet = new JTextField();
    static JTextField nam = new JTextField();

    JLabel second=new JLabel();
    JTextField correct=new JTextField();
    JTextField start = new JTextField();

    JLabel third = new JLabel();
    JTextField guess=new JTextField();
    JButton startguess = new JButton("确定");

    JLabel forth = new JLabel();
    JTextField scores = new JTextField();
    JTextArea note = new JTextArea();
    JScrollPane sc=new JScrollPane(note);

    Guess one = new Guess(length);
    int[] rnum = new int[length];
    String str="";
    public Game(){
        super("猜数字");
        Font font=new Font("楷体",Font.BOLD,30);
        startguess.setFont(font);
        setLayout(new BorderLayout());
        add(new ActMenu(), BorderLayout.NORTH);

        add(center,BorderLayout.CENTER);
        center.setLayout(new GridLayout(4,1));

        greet.setFont(new Font("楷体",Font.BOLD,25) );
        greet.setHorizontalAlignment(JTextField.CENTER);
        nam.setFont(new Font("楷体",Font.PLAIN,30) );
        nam.setHorizontalAlignment(JTextField.CENTER);
        center.add(first);
        first.setLayout(new GridLayout(1,2));
        first.add(greet);
        nam.setEditable(false);
        first.add(nam);
        greet.setEditable(false);
        greet.setText(Client.greetings(count));

        center.add(second);
        second.setLayout(new GridLayout(1,1));

        second.add(start);
        start.setHorizontalAlignment(JTextField.CENTER);
        correct.setEditable(false);
        start.setEditable(false);
        start.setFont(new Font("楷体",Font.PLAIN,50) );
        start.setText("请输入数字进行游戏");

        guess.setFont(new Font("楷体",Font.PLAIN,30) );
        guess.setHorizontalAlignment(JTextField.CENTER);
        center.add(third);
        third.setLayout(new GridLayout(1,2));
        third.add(guess);
        third.add(startguess);
        startguess.addActionListener(new ActLis());

        scores.setFont(new Font("楷体",Font.PLAIN,30) );
        scores.setHorizontalAlignment(JTextField.CENTER);
        note.setFont(new Font("楷体",Font.PLAIN,18));
        center.add(forth);
        forth.setLayout(new GridLayout(1,2));
        forth.add(scores);
        forth.add(sc);
        scores.setEditable(false);
        note.setOpaque(false);
        process();
    }

    public  void process() {




        rnum= one.rand(rnum);
        correct.setText(one.correct(rnum));


    }




    private class ActLis implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == startguess) {
                int[] gnum = new int[length];
                String gstr=guess.getText();
                gnum=one.guess(gstr,gnum);
                for(int i=0;i<=count-1;i++){
                    if(users[i].name==null) continue;
                    if(users[i].name.equals(nam.getText())){
                        temp=i;
                        break;
                    }
                }
                if(nam.getText().equals("")==true){
                    JOptionPane.showMessageDialog(null, "请您登录后再进行游戏", "尚未登录！", JOptionPane.ERROR_MESSAGE);
                }
                if(temp==-1&&nam.getText().equals("")==false) {
                    Game.count++;
                    Game.users[Game.count-1]=new Client();
                    str += one.compare(rnum, gnum, users[count - 1]);
                    note.setText(str);
                    users[count - 1].name = nam.getText();
                    scores.setText("" + users[count - 1].name + "当前得分:" + users[count - 1].scores);
                }
                else if(temp!=-1&&nam.getText().equals("")==false){
                    //if(mem_count==count) count--;
                    str += one.compare(rnum, gnum, users[temp]);
                    note.setText(str);
                    scores.setText("" + users[temp].name + "当前得分:" + users[temp].scores);
                }
                run.rank.printRank();
            }
        }
    }
}
