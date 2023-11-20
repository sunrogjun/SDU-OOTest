import java.io.*;

public class UserMessage
{
    public void write(String[] message)throws IOException
    {
        File file=new File("Message.txt");
        String messagesum="";
        for (int i=0; i<2; i++)
            messagesum+=message[i]+"~";
        if(!file.exists())
            file.createNewFile();
        FileOutputStream out=new FileOutputStream(file,true);
        StringBuffer sb=new StringBuffer();
        sb.append(messagesum+"\n");
        out.write(sb.toString().getBytes("gb2312"));
        out.close();
    }
    public String[] read(String countname) throws IOException
    {
        File file=new File("Message.txt");
        if(!file.exists()||file.isDirectory())
            throw new FileNotFoundException();
        BufferedReader br=new BufferedReader(new FileReader(file));
        String temp=null;
        StringBuffer sb=new StringBuffer();
        temp=br.readLine();

        String []message = new String[5];
        while(temp!=null){
            String sbstring = temp.toString();
            int n = sbstring.length();
            for (int i=0; i<5; i++)
                message[i] = "";

            int k=0;
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
            if (message[2].equals(countname))
            {
                return message;
            }
            temp=br.readLine();
        }
        return null;
    }



    public String updatepwd(String countname,String pwd) throws IOException
    {
        File file=new File("Message.txt");
        if(!file.exists()||file.isDirectory())
            throw new FileNotFoundException();
        BufferedReader br=new BufferedReader(new FileReader(file));
        String temp=null;
        StringBuffer sb=new StringBuffer();
        StringBuffer sb1=new StringBuffer();

        String moneystring="";

        temp=br.readLine();
        String []message = new String[2];
        while(temp!=null){
            String sbstring = temp.toString();
            int n = sbstring.length();
            for (int i=0; i<2; i++)
                message[i] = "";

            int k=0;
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

            if (message[0].equals(countname))
            {
                for (int i=0; i<3; i++)
                    sb1.append(message[i]+"~");
                sb1.append(pwd+"~");
                sb1.append(message[4]+"~\n");
            }
            else
            {
                sb1.append(temp+"\n");
            }
            temp=br.readLine();
        }
        File file1=new File("Message.txt");
        if(!file1.exists())
            file1.createNewFile();
        FileOutputStream out=new FileOutputStream(file1,false);
        out.write(sb1.toString().getBytes("gb2312"));
        out.close();

        return moneystring;
    }

}

