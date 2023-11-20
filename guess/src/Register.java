import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;

/*
 * 完成对注册页面的编写， 用户需填写 账户，密码
 *
 * 并且会进行验证操作， 如姓名是否合法（中文）
 *
 */
@SuppressWarnings("serial")
public class Register extends JFrame implements ActionListener{

    JButton jb1, jb2;
    JLabel jlb1, jlb2, jlb3,jlb4,jlb5, jlb6;
    JTextField jtf3,jtf4;
    JPasswordField jpf;
    JPanel jp3, jp4,jp6,jp7;

    public Register() {
        //按钮
        jb1 = new JButton("提交");
        jb1.setFont(new Font("楷体",Font.BOLD,18) );
        jb2 = new JButton("登录");
        jb2.setFont(new Font("楷体",Font.BOLD,18) );
        //设置按钮监听
        jb1.addActionListener(this);
        jb2.addActionListener(this);
        //标签信息

        jlb3 = new JLabel("     账号");
        jlb3.setFont(new Font("楷体",Font.BOLD,16) );
        jlb4 = new JLabel("     密码");
        jlb4.setFont(new Font("楷体",Font.BOLD,16) );
        jtf3 = new JTextField(10);
        jtf3.setFont(new Font("serial",Font.PLAIN,16) );
        jtf4 = new JTextField(10);
        jtf4.setFont(new Font("serial",Font.PLAIN,16) );

        jp3 = new JPanel();
        jp4 = new JPanel();
        jp6 = new JPanel();
        jp7 = new JPanel();

        jp3.add(jlb3);
        jp3.add(jtf3);

        jp4.add(jlb4);
        jp4.add(jtf4);


        jp6.add(jb1);
        jp6.add(jb2);


        this.add(jp3);
        this.add(jp4);

        this.add(jp6);

        //设置布局
        this.setTitle("注册信息");
        this.setLayout(new GridLayout(3,1));
        this.setSize(300, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.setVisible(true);
        this.setResizable(false);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand()=="提交")
        {
            try {
                register();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        else if (e.getActionCommand()=="登录")
        {
            new Login();
        }

    }
    public void register() throws IOException
    {
        if (jtf3.getText().isEmpty()||jtf4.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "信息有空缺，请补全！","消息提示",JOptionPane.WARNING_MESSAGE);
        }
        else if (!jtf3.getText().isEmpty()&&!jtf4.getText().isEmpty())
        {
            String []message = new String[2];

            message[0] = jtf3.getText();
            message[1] = jtf4.getText();
            if (!new Check().check2(message[0]))
            {
                new UserMessage().write(message);
                JOptionPane.showMessageDialog(null,"注册成功！","提示消息",JOptionPane.WARNING_MESSAGE);
                dispose();

                new Login();
            }
            else
            {
                JOptionPane.showMessageDialog(null,"账号已存在，请重新输入！","提示消息",JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    private void clear() {

        jtf3.setText("");
        jtf4.setText("");
    }
}

