package cn.edu.fzu.sm.zhangsan.stu.frame;

import cn.edu.fzu.sm.zhangsan.stu.dao.StudentDao;
import cn.edu.fzu.sm.zhangsan.stu.dao.impl.StudentDaoImpl;
import cn.edu.fzu.sm.zhangsan.stu.entity.Student;
import cn.edu.fzu.sm.zhangsan.stu.entity.StudentTableModel;
import cn.edu.fzu.sm.zhangsan.stu.entity.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentFrame extends JFrame{
    private User user;
    private JPanel contentPanel;
    private JTable tabel;
    private StudentDao studentDao =new StudentDaoImpl();
    private List<Student> rows;
    private ArrayList<String> cols;
    private StudentTableModel<Student> studentTableModel;
    private JPanel searchPane;
    private JTextField nameTF;

    public StudentFrame(User user) throws HeadlessException {
        this.user = user;
        this.setTitle("学生管理系统");
        this.setSize(800,600);
        this.setLocationRelativeTo(null);
        initUI();

    }

    private void initUI() {
        contentPanel =new JPanel();
        contentPanel.setBackground(Color.GRAY);
        contentPanel.setBorder(new EmptyBorder(5,5,5,5));
        contentPanel.setLayout(new BorderLayout(0,0));
        this.setContentPane(contentPanel);
        JLabel titleLabel=new JLabel("学生管理系统");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("楷体",0,36));
        titleLabel.setForeground(Color.BLUE);
        this.getContentPane().add(titleLabel,BorderLayout.PAGE_START);


        tabel=new JTable();
        cols=new ArrayList<>();
        cols.add("ID");
        cols.add("姓名");
        cols.add("年龄");
        cols.add("地址");
        cols.add("学号");
        cols.add("联系方式");

        rows=studentDao.findAllStudent();
        studentTableModel =new StudentTableModel<Student>(cols,rows);
        tabel.setModel(studentTableModel);
        JScrollPane jScrollPane=new JScrollPane();
        jScrollPane.setViewportView(tabel);


        this.getContentPane().add(jScrollPane,BorderLayout.CENTER);


        JMenuBar menuBar=new JMenuBar();
        JMenu menuEdit=new JMenu("编辑");
        JMenuItem menuItemInsert=new JMenuItem("插入");
        JMenuItem menuItemDelete=new JMenuItem("删除");
        JMenuItem menuItemUpdate=new JMenuItem("更改");

        this.setJMenuBar(menuBar);
        menuBar.add(menuEdit);
        menuEdit.add(menuItemInsert);
        menuEdit.add(menuItemDelete);
        menuEdit.add(menuItemUpdate);

        menuItemInsert.addActionListener(new InsertHandler());
        menuItemDelete.addActionListener(new DeleteHandler());
        menuItemUpdate.addActionListener(new UpdateHandler());


        searchPane=new JPanel();
        JLabel nameLabel=new JLabel("姓名: ");
        nameTF=new JTextField(16);
        JButton searchBtn=new JButton("检索");
        searchPane.add(nameLabel);
        searchPane.add(nameTF);
        searchPane.add(searchBtn);

        searchBtn.addActionListener(new SearchHandler());
        this.add(searchPane,BorderLayout.SOUTH);

    }

    public void updateTable() {
        rows=studentDao.findAllStudent();
        studentTableModel.setRows(rows);
        studentTableModel.fireTableDataChanged();
    }

    private class InsertHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new InsertStudentFrame(StudentFrame.this).setVisible(true);
        }
    }

    private class DeleteHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = tabel.getSelectedRow();
            Student student=rows.get(selectedRow);
            boolean result=studentDao.deleteStudentById(student.getSid());
            if(result){
                updateTable();
                JOptionPane.showMessageDialog(StudentFrame.this,"删除成功");
            }else{
                JOptionPane.showMessageDialog(StudentFrame.this,"删除失败");
            }

        }
    }

    private class UpdateHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = tabel.getSelectedRow();
            Student student=rows.get(selectedRow);
            new UpdateStudentFrame(StudentFrame.this,student).setVisible(true);
        }
    }

    private class SearchHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String enteredName=nameTF.getText();
            if(enteredName==null||enteredName.length()==0){
                JOptionPane.showMessageDialog(StudentFrame.this,"请输入要查询的名字");
                return;
            }else{
                rows=studentDao.searchStudent(enteredName);
                studentTableModel.setRows(rows);
                studentTableModel.fireTableDataChanged();
            }
        }
    }
}
