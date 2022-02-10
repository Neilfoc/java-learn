package spi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author 11105157
 * @Description
 * @Date 2022/2/3
 */
public class SpiTest {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        // 1.Mysql的Driver例子
        Class.forName("com.mysql.jdbc.Driver");//先创建一个Driver对象放到DriverManager.registeredDrivers 集合中
        // getConnection的时候，DriverManager 从该 registeredDrivers 集合中获取对应的 Driver 对象创建 Connection
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "123456");
        System.out.println(connection);



        // 2.jdk的SPI例子
        /*ServiceLoader<Log> serviceLoader = ServiceLoader.load(Log.class);// JDK SPI 的入口方法,底层会生成一个ServiceLoader的内部类LazyIterator
        Iterator<Log> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            Log log = iterator.next();
            log.log("JDK SPI");
        }*/
    }
}
