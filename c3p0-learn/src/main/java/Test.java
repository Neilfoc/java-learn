import com.alibaba.fastjson.JSON;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Test {

    public static void main(String[] args) {
        try {
            ComboPooledDataSource cpds = new ComboPooledDataSource();
            cpds.setDriverClass("com.mysql.jdbc.Driver"); //loads the jdbc driver
            cpds.setJdbcUrl("jdbc:mysql://localhost:3306/test");
            cpds.setUser("neilfoc");
            cpds.setPassword("123456");
            cpds.setAutomaticTestTable("abc");

            Connection connection = cpds.getConnection();
            String sql = "select count(*) as numCount from name";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("rs:" + JSON.toJSONString(rs.getString(1)));
            }

            Connection connectionNew = cpds.getConnection();
            String sqlnew = "select count(*) as numCount from person";
            PreparedStatement psnew = connectionNew.prepareStatement(sqlnew);
            ResultSet rsnew = psnew.executeQuery();
            if (rsnew.next()) {
                System.out.println("rsnew:" + JSON.toJSONString(rsnew.getString(1)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
