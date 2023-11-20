import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.*;

public class Rank extends JFrame{
    JTextArea d = new JTextArea();
    public Rank(){
        super("猜数字");
        setLayout(new BorderLayout());
        add(new ActMenu(), BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(
                d,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        add(scrollPane,BorderLayout.CENTER);
        d.setEditable(false);
        d.setOpaque(false);
        d.setLineWrap(true);
        d.setFont(new Font("楷体",0,22));

        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.out.println("Closed");
                e.getWindow().dispose();
            }
        });
    }

    public Client[] rank(){
        Client[] cli=new Client[Game.count];
        for(int i=0;i<cli.length;i++)
            cli[i]=Game.users[i];
        for(int i=0;i<Game.count;i++){
            Client temp=cli[i];
            int n=i;
            for(int j=i+1;j<Game.count;j++){
                if(cli[j].scores>temp.scores){
                    temp=cli[j];
                    n=j;
                }
            }
            cli[n]=cli[i];
            cli[i]=temp;
        }

        return cli;
    }

    public String printRank(){
        Client[] cli=rank();
        String str="\t      排 名          用 户 名          总 分\n\n";
        String temp_name,temp_score,temp_no;
        for(int i=0;i<Game.count;i++) {
            temp_name=cli[i].name;
            int temp_len=cli[i].name.length();
            for(int j=0;j<cli[i].name.length();j++){
                if((cli[i].name.charAt(j) >= 0x4e00)&&(cli[i].name.charAt(j) <= 0x9fbb)) {temp_len++;}
            }
            for(int j=1;j<=18-temp_len;j++)
                temp_name+=" ";
            temp_score = String.valueOf(cli[i].scores);
            for(int j=1;j<=12-temp_score.length();j++)
                temp_score+=" ";
            temp_no = String.valueOf(i+1);
            for(int j=1;j<=16-temp_no.length();j++)
                temp_no+=" ";
            str += "\t        " +temp_no+ "      " + temp_name  + temp_score + "\n\n";
        }
        File file = new File("Rank.txt");
        String messagesum = "";
        for (int i = 0; i < Game.count; i++) {
            if(cli[i].name==null) continue;
            messagesum += cli[i].name + "~";
            messagesum+=String.valueOf(cli[i].scores);
            if(i!=Game.count-1) messagesum+="\n";
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file, false);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        StringBuffer sb = new StringBuffer();
        sb.append(messagesum + "\n");
        try {
            out.write(sb.toString().getBytes("gb2312"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return str;
    }
}
