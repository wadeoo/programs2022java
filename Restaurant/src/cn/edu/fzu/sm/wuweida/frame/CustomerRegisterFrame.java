package cn.edu.fzu.sm.wuweida.frame;

import cn.edu.fzu.sm.wuweida.bean.User;
import cn.edu.fzu.sm.wuweida.dao.JdbcHelper;
import cn.edu.fzu.sm.wuweida.dao.ManageHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerRegisterFrame extends JFrame {
    public CustomerRegisterFrame() throws HeadlessException {
        super("注册");
        this.setSize(400, 250);
        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
        this.setType(Type.UTILITY);


        JLabel usernameLabel = new JLabel("用户名:");
        JLabel passwordLabel = new JLabel("密码: ");
        JLabel passwordConfirmLabel = new JLabel("请再输入一遍密码:");

        JTextField usernameTextField = new JTextField(16);
        JPasswordField passwordField = new JPasswordField(16);
        JPasswordField passwordConfirmField = new JPasswordField(16);
        passwordField.setEchoChar('*');
        passwordConfirmField.setEchoChar('*');

        usernameTextField.grabFocus();

        JButton okBtn = new JButton("确认注册");
        JButton cancelBtn = new JButton("取消");
        JButton returnToLoginBtn=new JButton("回到登录界面");

        JPanel usernamePanel = new JPanel();
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameTextField);
        JPanel passwordPanel = new JPanel();
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        JPanel passwordConfirmPanel = new JPanel();
        passwordConfirmPanel.add(passwordConfirmLabel);
        passwordConfirmPanel.add(passwordConfirmField);
        JPanel actionPanel = new JPanel();
        actionPanel.add(okBtn);
        actionPanel.add(cancelBtn);
        actionPanel.add(returnToLoginBtn);


        this.add(usernamePanel);
        this.add(passwordPanel);
        this.add(passwordConfirmPanel);
        this.add(actionPanel);

        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameText = usernameTextField.getText();
                String passwordText = passwordField.getText();
                String passwordConfirmText = passwordConfirmField.getText();
                JdbcHelper jdbcHelper = new JdbcHelper();

                if (usernameText == null || usernameText.equals("")) {
                    JOptionPane.showMessageDialog(CustomerRegisterFrame.this, "请输入用户名");
                } else if (passwordText == null || passwordText.equals("")) {
                    JOptionPane.showMessageDialog(CustomerRegisterFrame.this, "请输入密码");
                } else if (passwordConfirmText == null || passwordConfirmText.equals("")) {
                    JOptionPane.showMessageDialog(CustomerRegisterFrame.this, "请再输入一次密码");
                } else if (!passwordText.equals(passwordConfirmText)) {
                    JOptionPane.showMessageDialog(CustomerRegisterFrame.this, "两次密码不一致!");
                    passwordField.setText("");
                    passwordConfirmField.setText("");
                } else {
                    if (jdbcHelper.isUsernameUsed(usernameText)) {
                        JOptionPane.showMessageDialog(CustomerRegisterFrame.this, "该用户名已存在");
                        usernameTextField.setText("");
                    } else {
                        User newUser = new User();
                        newUser.setUsername(usernameText);
                        newUser.setPassword(passwordText);
                        if (jdbcHelper.register(newUser)) {
                            CustomerRegisterFrame.this.dispose();
                            CustomerLoginFrame customerLoginFrame = new CustomerLoginFrame();
                        }
                    }
                }
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomerRegisterFrame.this.dispose();
            }
        });

        returnToLoginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomerRegisterFrame.this.dispose();
                CustomerLoginFrame customerLoginFrame=new CustomerLoginFrame();
            }
        });

        this.setVisible(true);
    }

}
