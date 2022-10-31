package cn.edu.fzu.sm.zhangsan.stu.frame;

import cn.edu.fzu.sm.zhangsan.stu.db.DB;
import cn.edu.fzu.sm.zhangsan.stu.entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private JTextField usernameTF;
    private JPasswordField passwordF;

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


        usernameTF = new JTextField(16);
        passwordF=new JPasswordField(16);

        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameTF);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordF);
        this.add(usernamePanel);
        this.add(passwordPanel);


        JPanel loginPanel=new JPanel();
        JButton loginBtn=new JButton("登录");
        JButton cancelBtn=new JButton("取消");

        loginBtn.addActionListener(new LoginHandler());


        loginPanel.add(loginBtn);
        loginPanel.add(cancelBtn);
        this.add(loginPanel);
    }

    private class LoginHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username=usernameTF.getText();
            //String password=passwordF.getPassword().toString();
            String password=passwordF.getText();

            if (username==null||username.length()==0){
                JOptionPane.showMessageDialog(LoginFrame.this,"请输入用户名");
                return;
            }else if(password==null||password.length()==0){
                JOptionPane.showMessageDialog(LoginFrame.this,"请输入密码");
                return;
            }
            String sql="select * from user where username='"+username+"' and password='"+password+"';";
            try{
                ResultSet rs= DB.runQuery(sql);
                if(rs.next()){
                    User user=new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setName(rs.getString("name"));
                    StudentFrame studentFrame=new StudentFrame(user);
                    studentFrame.setVisible(true);
                    LoginFrame.this.setVisible(false);
                }else{
                    JOptionPane.showMessageDialog(LoginFrame.this,"数据库连接出现问题");
                }
            }catch (SQLException e1){
                JOptionPane.showMessageDialog(LoginFrame.this,"");
            }
        }
    }
}
