package tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MysqlConTest {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");//加载数据库驱动
            //连接MySql数据库，用户名和密码都是root
            String url = "jdbc:mysql://localhost:3306/test" ;
            String username = "root" ;
            String password = "123456";
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");//连接数据库
            Statement statement = conn.createStatement();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
