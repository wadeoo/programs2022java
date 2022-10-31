package cn.edu.fzu.sm.zhangsan.stu.db;

import com.mysql.jdbc.StringUtils;

import java.sql.*;

//连接并访问数据库的类
public class DB {
    String JDBC_DRIVER="com.mysql.jdbc.Driver";
    static final String DB_URL="jdbc:mysql://localhost:3306/mystu?useUnicode=true&characterEncoding=UTF-8";
    static final String USER="root";
    static final String PWD="haosql";
    static Connection conn=null;
    static Statement statement=null;//状态
    static ResultSet resultSet=null;//查询结果 结果集



    public static Connection getConn(){
        if(conn==null){//如果没有连接,或者连接已被关闭
            try{
                conn= DriverManager.getConnection(DB_URL,USER,PWD);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return conn;
    }

    public static Statement getStatement(){//获取当前连接的状态
        if(statement==null){
            try{
                statement=getConn().createStatement();//获取数据库连接,并创建连接状态
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return statement;
    }

    public static ResultSet runQuery(String sql) throws SQLException{
        return getStatement().executeQuery(sql);
    }

    public static int  runUpdateQuery(String sql) throws SQLException {
        return getStatement().executeUpdate(sql);
    }
}
