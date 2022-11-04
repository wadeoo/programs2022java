package cn.edu.fzu.sm.wuweida.dao;

import cn.edu.fzu.sm.wuweida.bean.User;

import java.sql.*;

//与数据库通信的类

public class JdbcHelper implements  JdbcConfig{
    private PreparedStatement preparedStatement=null;
    private ResultSet resultSet=null;
    private Connection connection=null;

    //获取数据库的连接
    private void init(){
        try {
            Class.forName(DRIVER);
            connection= DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public JdbcHelper(){
        this.init();
    }


    /*获取用户对象 */
    public User getUser(User user){
        User newUser=new User();

        try {
            preparedStatement=connection.prepareStatement("select * from user where username=?");
            preparedStatement.setString(1,user.getUsername());
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                newUser.setUsername(resultSet.getString(1));
                newUser.setPassword(resultSet.getString(2));
                newUser.setIsLogin(resultSet.getInt(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newUser;
    }

    public boolean register(User user){
        boolean  b=false;
        try {
            preparedStatement=connection.prepareStatement("insert into user(username,password) value(?,?)");
            preparedStatement.setString(1,user.getUsername());
            preparedStatement.setString(2,user.getPassword());
            if(preparedStatement.executeUpdate()>0){
                b=true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }

    /*修改用户登录状态*/
    public boolean update_isLogin(User user){
        boolean b=false;
        try{
            preparedStatement=connection.prepareStatement("update user set islogin=? where username=?");
            preparedStatement.setInt(1,user.getIsLogin());
            preparedStatement.setString(2,user.getUsername());
            if(preparedStatement.executeUpdate()>0){
                b=true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return b;
    }

    /*修改密码*/
    public boolean update_password(User user,String newPassword){
        boolean b=false;
        try{
            preparedStatement=connection.prepareStatement("update user set password=? where username=?");
            preparedStatement.setString(1,newPassword);
            preparedStatement.setString(2,user.getUsername());
            if(preparedStatement.executeUpdate()>0){
                b=true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return b;
    }

}
