package cn.edu.fzu.sm.zhangsan.stu.dao;

import cn.edu.fzu.sm.zhangsan.stu.entity.Student;

import java.util.List;

public interface StudentDao {
    public List<Student> findAllStudent();

    boolean addStudent(Student student);

    boolean deleteStudentById(int sid);

    boolean updateStudent(Student student);

    List<Student> searchStudent(String enteredName);
}
