package rmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author 11105157
 * @Description
 * @Date 2021/1/10
 */
public class Producer {

    public static void main(String[] args) throws Exception {

        System.out.println("Producer start...");


        Connection connection = ConnectionUtil.getConnection();


        Channel channel = connection.createChannel();


        for(int i=0; i < 5; i++){
            String msg = "Hello RabbitMQ!";

            channel.basicPublish("", "test001", null, msg.getBytes());
        }


        channel.close();
        connection.close();
    }
}
