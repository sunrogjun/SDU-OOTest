import java.util.Random;
import java.util.Scanner;
import java.util.*;

public class Guess {
    int length;
    public Guess(int lenth){
        this.length = lenth;
    }

    public int[] rand(int[] rnum) {

        Random random = new Random();

        for (int i = 0; i < rnum.length;) {
            int number = random.nextInt(10);
            boolean uncopy = true;
            for (int j = 0; j < i; j++) {
                if (number == rnum[j])
                    uncopy = false;
            }

            if (uncopy) {
                rnum[i] = number;
                i++;
            }
        }
        return rnum;
    }

    public int[] guess(String str, int[] gnum){
        char[] cs = str.toCharArray();
        for(int i=0; i<gnum.length; i++)
            gnum[i]=cs[i]-'0';
        return gnum;
    }

    public String compare(int[] rnum,int[] gnum,Client user){
        int n=0,m=0,len=rnum.length;
        boolean[] exact = new boolean[len];
        for(int i=0;i<len;i++)
            if(rnum[i]==gnum[i]){
                n++;
                exact[i]=true;
            }
        for(int i=0;i<len;i++){
            if(exact[i])
                continue;
            for(int j=0;j<len;j++)
                if(gnum[i]==rnum[j])
                    m++;
        }
        String result;
        if(n==len){
            result = n+"A"+"      猜对了!\n";
            user.winscores();

        }
        else{
            result = n+"A"+m+"B"+"    再试试吧!\n";

        }

        return result;
    }

    public String correct(int[] rnum){
        String str="正确答案为：";
        for(int i=0;i<rnum.length;i++)
            str+=rnum[i];
        System.out.println(str);
        return str;
    }

}
