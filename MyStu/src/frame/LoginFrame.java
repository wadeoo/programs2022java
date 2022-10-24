package frame;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    public LoginFrame() throws HeadlessException {
        this.setTitle("用户登录");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(400,200);
        this.setLocationRelativeTo(null);
        ImageIcon imageIcon=new ImageIcon("././img/login.png");
        this.setIconImage(imageIcon.getImage());
        this.setType(Type.UTILITY);


        initUI();
    }

    private void initUI() {
        this.setLayout(new GridLayout(3,1));

        JPanel usernamePanel=new JPanel();
        JPanel passwordPanel=new JPanel();


        JLabel usernameLabel=new JLabel("用户名: ");
        JLabel passwordLabel=new JLabel("密码: ");


        JTextField usernameTF = new JTextField(16);
        JPasswordField passwordF=new JPasswordField(16);

        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameTF);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordF);
        this.add(usernamePanel);
        this.add(passwordPanel);


        JPanel loginPanel=new JPanel();
        JButton loginBtn=new JButton("登录");
        JButton cancelBtn=new JButton("取消");
        loginPanel.add(loginBtn);
        loginPanel.add(cancelBtn);
        this.add(loginPanel);
    }
}
