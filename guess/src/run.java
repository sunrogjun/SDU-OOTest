import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.*;

import javax.swing.JFrame;

public class run {
    static Start start=new Start();
    static Game game=new Game();
    static Rank rank=new Rank();
    static JFrame[] frames = { start,game,rank};
    public static void main(String args[]) throws InterruptedException {
        File file=new File("Rank.txt");
        Game.count=0;
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String temp=null;
        try {
            InputStreamReader fReader = new InputStreamReader(new FileInputStream("Rank.txt"),"GB2312");
            BufferedReader br = new BufferedReader(fReader);
            temp=br.readLine();
            while(temp!=null){
                String sbstring = temp.toString();
                int n = sbstring.length();
                String []message = new String[5];
                int k=0;

                for (int i=0; i<2; i++)
                    message[i]="";
                for (int i=0; i<n; i++)
                {
                    if(sbstring.charAt(i)=='~')
                    {
                        k++;
                    }
                    else
                    {
                        message[k] += sbstring.charAt(i);
                    }
                }
                Game.temp=-1;
                Game.count++;
                Game.mem_count=Game.count;
                Game.users[Game.count-1]=new Client();
                Game.users[Game.count-1].name=message[0];
                if(Game.users[Game.count-1].name==null||Game.users[Game.count-1].name.equals("")) {Game.count--;
                    System.out.println("!!!");
                    break;
                }
                Game.users[Game.count-1].scores=Integer.parseInt(message[1]);
                temp=br.readLine();
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Game.temp=-1;
        for (int index = 0; index < frames.length; index++) {
            frames[index].setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frames[index].setPreferredSize(new Dimension(700,750));
            screencentre(frames[index]);

            frames[index].pack();
        }
        frames[0].setVisible(true);
    }
    public static void screencentre(JFrame frame) {

        Toolkit kit = Toolkit.getDefaultToolkit();

        Dimension screenSize = kit.getScreenSize();

        int screenWidth = screenSize.width;

        int screenHeight = screenSize.height;


        frame.setLocation(screenWidth/4, screenHeight/15);
    }
}
