package rmq;

import com.rabbitmq.client.*;
import lombok.SneakyThrows;

import java.io.IOException;

/**
 * @author 11105157
 * @Description
 * @Date 2021/1/10
 */
public class ClientConfig {
    /**
     * 创建连接信道
     * @return
     */
    @SneakyThrows
    public static Channel createChannel(){
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("RabbitMQ应用服务安装IP地址");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");

        Connection connection = factory.newConnection();

        return connection.createChannel();
    }

    /**
     * 推送消息
     * @param channel
     * @param message
     */
    @SneakyThrows
    public static void publishMessage(Channel channel,byte[] message){
        String exchangeName = "exchangeName";
        String queueName = "queueName";
        String binding = "binding";
        String routingKey = binding;

        boolean durable = true , autoDelete = false;
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT,durable,autoDelete,null);

        boolean exclusive = false;
        channel.queueDeclare(queueName,durable,exclusive,autoDelete,null);

        channel.queueBind(queueName,exchangeName,binding);

        channel.basicPublish(exchangeName,routingKey,null,message);

    }

    /**
     * 消费消息
     * @param channel
     */
    @SneakyThrows
    public static void consumeMessage(Channel channel){
        String queueName = "queueName";
        String consumerTag = "consumerTag";

        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery (String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {

            }
        };

        boolean autoAck = true;
        channel.basicConsume(queueName,autoAck,consumerTag,consumer);
    }

    /**
     * 销毁连接信道
     * @param connection
     * @param channel
     */
    @SneakyThrows
    public static void destroyConnection(Connection connection,Channel channel){
        channel.close();
        connection.close();
    }

    @SneakyThrows
    public static void main (String[] args) {
        Channel channel = createChannel();
        String queueName = "queueName" , message = "测试消息";
        channel.basicPublish("",queueName,null,message.getBytes());
    }

}
