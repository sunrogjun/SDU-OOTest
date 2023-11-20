import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;

@SuppressWarnings("serial")
public class Login extends JFrame implements ActionListener{

    JButton jb1, jb2, jb3;
    JPanel jp1,jp2,jp3, jp4;
    JTextField jtf;
    JLabel jlb1, jlb2, jlb3;
    JPasswordField jpf;
    static String Name;

    public  Login() {


        jb1 = new JButton("登录");
        jb1.setFont(new Font("楷体",Font.BOLD,18) );
        jb2 = new JButton("注册");
        jb2.setFont(new Font("楷体",Font.BOLD,18) );
        //设置按钮监听
        jb1.addActionListener(this);
        jb2.addActionListener(this);

        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();
        jp4 = new JPanel();


        jlb1 = new JLabel("用户名");
        jlb1.setFont(new Font("楷体",Font.BOLD,16) );
        jlb2 = new JLabel("密  码");
        jlb2.setFont(new Font("楷体",Font.BOLD,16) );

        jtf = new JTextField(10);
        jtf.setFont(new Font("serial",Font.PLAIN,16) );
        jpf = new JPasswordField(10);
        jpf.setFont(new Font("serial",Font.PLAIN,16) );


        jp1.add(jlb1);
        jp1.add(jtf);

        jp2.add(jlb2);
        jp2.add(jpf);

        jp3.add(jb1);
        jp3.add(jb2);



        this.add(jp1);
        this.add(jp2);
        this.add(jp3);



        this.setTitle("用户登录");
        this.setLayout(new GridLayout(3,1));
        this.setSize(300, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.setVisible(true);
        this.setResizable(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand()=="登录")
        {
            try {
                Name=jtf.getText();
                Game.nam.setText(Name);
                login();
            } catch (HeadlessException | IOException e1) {
                e1.printStackTrace();
            }
        }
        else if(e.getActionCommand()=="注册")
        {
            new Register();
            dispose();
        }
    }
    private void clear() {
        jtf.setText("");
        jpf.setText("");

    }

    @SuppressWarnings("deprecation")
    public  void login() throws HeadlessException, IOException
    {
        if(jtf.getText().isEmpty()&&jpf.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "账号密码为空，请输入！","消息提示",JOptionPane.WARNING_MESSAGE);
            Game.nam.setText(null);
        }
        else if (jtf.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "账号为空，请输入！","消息提示",JOptionPane.WARNING_MESSAGE);
            Game.nam.setText(null);
        }
        else if (jpf.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "密码为空，请输入！","消息提示",JOptionPane.WARNING_MESSAGE);
            Game.nam.setText(null);

        }
        else if (new Check().check1(jtf.getText(),jpf.getText()))
        {
            JOptionPane.showMessageDialog(null,"登录成功！","提示消息",JOptionPane.WARNING_MESSAGE);
            dispose();
        }
        else
        {
            JOptionPane.showMessageDialog(null, "账号密码错误请重新输入！","消息提示",JOptionPane.ERROR_MESSAGE);
            Game.nam.setText(null);
            clear();
        }
    }

}

