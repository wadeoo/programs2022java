package cn.edu.fzu.sm.wuweida.frame;

import cn.edu.fzu.sm.wuweida.bean.User;
import cn.edu.fzu.sm.wuweida.dao.ManageHelper;
import cn.edu.fzu.sm.wuweida.util.ImagePanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerLoginFrame extends JFrame {
    public CustomerLoginFrame(){
        super("欢迎光临");

//        this.setContentPane(new ImagePanel());

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon(CustomerLoginFrame.class.getResource("/restaurant.png")).getImage());

        this.setType(Type.UTILITY);
        this.setLayout(new GridLayout(3,1));
        this.setSize(400,150);
        this.setLocationRelativeTo(null);


        JLabel usernameLabel=new JLabel("用户名");
        JLabel passwordLabel=new JLabel("密码");

        JTextField usernameTextField=new JTextField(16);
        JPasswordField passwordField=new JPasswordField(16);
        passwordField.setEchoChar('*');

        usernameTextField.grabFocus();

        JButton okBtn=new JButton("登陆");
        JButton cancelBtn=new JButton("取消");
        JButton registerBtn=new JButton("注册");

        JPanel usernamePanel=new JPanel();
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameTextField);
        JPanel passwordPanel=new JPanel();
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        JPanel actionPanel=new JPanel();
        actionPanel.add(okBtn);
        actionPanel.add(cancelBtn);
        actionPanel.add(registerBtn);


        this.add(usernamePanel);
        this.add(passwordPanel);
        this.add(actionPanel);

        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameText=usernameTextField.getText();
                String passwordText=passwordField.getText();
                if(usernameText==null||usernameText.equals("")){
                    JOptionPane.showMessageDialog(CustomerLoginFrame.this,"请输入用户名");
                }else if(passwordText==null||passwordText.equals("")){
                    JOptionPane.showMessageDialog(CustomerLoginFrame.this,"请输入密码");
                }else{
                    User enteredUser=new User();
                    enteredUser.setUsername(usernameText);
                    enteredUser.setPassword(passwordText);
                    ManageHelper manageHelper=new ManageHelper();
                    if(manageHelper.login(enteredUser)){
                        CustomerMainFrame customerMainFrame=new CustomerMainFrame(usernameText);
                        CustomerLoginFrame.this.dispose();
                    }
                }
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomerLoginFrame.this.dispose();
            }
        });

        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomerLoginFrame.this.dispose();
                CustomerRegisterFrame customerRegisterFrame=new CustomerRegisterFrame();
            }
        });


        this.setVisible(true);

    }
}
