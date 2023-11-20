import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Check {

    /*
     * 在登录时， 验证账号密码是否正确
     */
    public boolean  check1(String countname,String pwd) throws IOException{

        File file=new File("Message.txt");
        if(!file.exists()||file.isDirectory())
            file.createNewFile();

        InputStreamReader fReader = new InputStreamReader(new FileInputStream("Message.txt"),"GB2312");
        BufferedReader br = new BufferedReader(fReader);
        String temp=null;
        temp=br.readLine();   //先读取一行
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
            if (countname.equals(message[0])&&pwd.equals(message[1]))
                return true;
            temp=br.readLine();
        }
        return false;


    }
    public boolean  check2(String countname) throws IOException{

        File file=new File("Message.txt");
        if(!file.exists()||file.isDirectory())
            file.createNewFile();
        BufferedReader br=new BufferedReader(new FileReader(file));
        String temp=null;
        temp=br.readLine();   //先读取一行
        while(temp!=null){
            String sbstring = temp.toString();
            int n = sbstring.length();
            String []message = new String[5];
            int k=0;

            for (int i=0; i<5; i++)
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
            if (countname.equals(message[0]))
                return true;
            temp=br.readLine();
        }
        return false;
    }


}

