package cn.edu.fzu.sm.wuweida.dao;

import cn.edu.fzu.sm.wuweida.bean.User;

import java.sql.SQLException;

public class ManageHelper {
    private  JdbcHelper jdbcHelper=new JdbcHelper();


    /*登录业务处理*/
    public boolean login(User user){
        boolean b=false;
        User dbUser=jdbcHelper.getUser(user);
        if(dbUser.getPassword().equals(user.getPassword())){
            b=true;
        }
        return b;
    }
}
