package rmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author 11105157
 * @Description
 * @Date 2021/1/10
 */
public class Consumer {


    public static void main(String[] args) throws Exception {

        System.out.println("Consumer start...");


        Connection connection = ConnectionUtil.getConnection();


        Channel channel = connection.createChannel();


        String queueName = "test001";
        channel.queueDeclare(queueName, true, false, false, null);

        channel.basicConsume(queueName, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                String message = new String(body, "utf-8");
                System.out.println("received: "+message);
            }

        });



    }
}
