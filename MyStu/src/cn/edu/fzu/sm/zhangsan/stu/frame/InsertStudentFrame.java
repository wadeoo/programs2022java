package cn.edu.fzu.sm.zhangsan.stu.frame;

import cn.edu.fzu.sm.zhangsan.stu.dao.StudentDao;
import cn.edu.fzu.sm.zhangsan.stu.dao.impl.StudentDaoImpl;
import cn.edu.fzu.sm.zhangsan.stu.entity.Student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertStudentFrame extends JFrame {
    private JTextField nameTF,ageTF,phoneTF,addressTF,numberTF;
    StudentFrame studentFrame;

    public InsertStudentFrame(StudentFrame studentFrame) throws HeadlessException {
        this.studentFrame=studentFrame;
        this.setTitle("添加学生信息");
        this.setSize(400,400);
        this.setLocationRelativeTo(null);
        
        initUI();
    }

    private void initUI() {

        //主面板
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5,5,5,5));
        contentPane.setBackground(Color.GRAY);
        contentPane.setLayout(null);

        JLabel nameLabel=new JLabel("姓名");
        nameLabel.setBounds(90,50,60,20);
        JLabel ageLabel=new JLabel("年龄");
        ageLabel.setBounds(90,90,60,20);
        JLabel numberLabel=new JLabel("学号");
        numberLabel.setBounds(90,130,60,20);
        JLabel phoneLabel=new JLabel("电话");
        phoneLabel.setBounds(90,170,60,20);
        JLabel addressLabel=new JLabel("地址");
        addressLabel.setBounds(90,210,60,20);

        nameTF=new JTextField();
        nameTF.setBounds(180,50,100,20);
        ageTF=new JTextField();
        ageTF.setBounds(180,90,100,20);
        numberTF=new JTextField();
        numberTF.setBounds(180,130,100,20);
        phoneTF=new JTextField();
        phoneTF.setBounds(180,170,100,20);
        addressTF=new JTextField();
        addressTF.setBounds(180,210,100,20);

        JButton btnAdd=new JButton("添加");
        btnAdd.setBounds(150,250,100,25);
        btnAdd.addActionListener(new addHandler());

        contentPane.add(nameLabel);
        contentPane.add(nameTF);
        contentPane.add(ageLabel);
        contentPane.add(ageTF);
        contentPane.add(numberLabel);
        contentPane.add(numberTF);
        contentPane.add(phoneLabel);
        contentPane.add(phoneTF);
        contentPane.add(addressLabel);
        contentPane.add(addressTF);

        contentPane.add(btnAdd);

        this.setContentPane(contentPane);
    }

    private class addHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name_s=nameTF.getText();
            String age_s=ageTF.getText();
            String number_s=numberTF.getText();
            String address_s=addressTF.getText();
            String phone_s=phoneTF.getText();

            if(name_s==null||name_s.length()==0){
                JOptionPane.showMessageDialog(InsertStudentFrame.this,"请输入学生姓名");
                return;
            }
            Student student=new Student();
            student.setSname(name_s);
            student.setSage(Integer.parseInt(age_s));
            student.setSnumber(number_s);
            student.setSphone(phone_s);
            student.setSaddress(address_s);
            StudentDao studentDao=new StudentDaoImpl();
            boolean result=studentDao.addStudent(student);
            if(result){
                studentFrame.updateTable();
                JOptionPane.showMessageDialog(InsertStudentFrame.this,"添加成功");
            }else{
                JOptionPane.showMessageDialog(InsertStudentFrame.this,"添加失败");
            }

        }
    }
}
