package rmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author 11105157
 * @Description
 * @Date 2021/1/11
 */
public class ConnectionUtil {
    public static Connection getConnection() throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("127.0.0.1");

        factory.setPort(5672);

        //factory.setVirtualHost("/vhost_cp");
        factory.setUsername("guest");
        factory.setPassword("guest");

        Connection connection = factory.newConnection();
        return connection;
    }
}
