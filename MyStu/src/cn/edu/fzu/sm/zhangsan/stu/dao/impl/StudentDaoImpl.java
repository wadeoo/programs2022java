package cn.edu.fzu.sm.zhangsan.stu.dao.impl;

import cn.edu.fzu.sm.zhangsan.stu.dao.StudentDao;
import cn.edu.fzu.sm.zhangsan.stu.db.DB;
import cn.edu.fzu.sm.zhangsan.stu.entity.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl  implements StudentDao {
    @Override
    public List<Student> findAllStudent() {
        String sql="select * from student";

        try{
            ResultSet rs= DB.runQuery(sql);
            List<Student> studentList=new ArrayList<>();
            while(rs.next()){
                Student student=new Student();
                student.setSid(rs.getInt("sid"));
                student.setSname(rs.getString("sname"));
                student.setSnumber(rs.getString("snumber"));
                student.setSage(rs.getInt("sage"));
                student.setSphone(rs.getString("sphone"));
                student.setSaddress(rs.getString("saddress"));
                studentList.add(student);
            }
            return studentList;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addStudent(Student student) {
        String sql="insert into student(sname,snumber,sage,sphone,saddress) values('"+student.getSname()+"','"+student.getSnumber()
                +"',"+student.getSage()+",'"+student.getSphone()+"','"+student.getSaddress()+"')";
        try {
            DB.runUpdateQuery(sql);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean deleteStudentById(int sid) {
        String sql="delete from student where sid="+sid;
        try {
            DB.runUpdateQuery(sql);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean updateStudent(Student student) {
        String sql="update student set sname='"+student.getSname()+"', snumber='"+student.getSnumber()
                +"', sage="+student.getSage()+", sphone='"+student.getSphone()+"', saddress='"+student.getSaddress()
                +"' where sid="+student.getSid();
        try {
            DB.runUpdateQuery(sql);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<Student> searchStudent(String enteredName) {
        String sql="select * from student where sname='"+enteredName+"'";
        try{
            ResultSet rs= DB.runQuery(sql);
            List<Student> studentList=new ArrayList<>();
            while(rs.next()){
                Student student=new Student();
                student.setSid(rs.getInt("sid"));
                student.setSname(rs.getString("sname"));
                student.setSnumber(rs.getString("snumber"));
                student.setSage(rs.getInt("sage"));
                student.setSphone(rs.getString("sphone"));
                student.setSaddress(rs.getString("saddress"));
                studentList.add(student);
            }
            return studentList;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
