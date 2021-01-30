package com.jsonyao.cs.confirmlistener;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Consumer: 测试Fanout交换机
 */
public class Receiver extends BaseClient {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // 1. 创建ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(HOST);
        connectionFactory.setPort(PORT);
        connectionFactory.setVirtualHost(VIRTUAL_HOST);

        // 2. 创建Connection
        Connection connection = connectionFactory.newConnection();

        // 3. 创建Channel
        Channel channel = connection.createChannel();

        // 4. 声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE, true, false, false,null);

        // 5. 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 6. Queue绑定Exchange: 表示交换机EXCHANGE_NAME上ROUTING_KEY的消息会路由到QUEUE_NAME中
        String routingKey = ROUTING_KEY;
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, routingKey);

        // 7. 创建Consumer
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, false, queueingConsumer);

        // 8. 拉取消息
        System.err.println("consumer1 start.. ");
        System.out.println("开始拉取消息, 绑定的RoutingKey为: " + routingKey);
        while (true){
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("收到消息: " + msg + ", RoutingKey: " + delivery.getEnvelope().getRoutingKey());

            // 手工签收信息ACK ------- ok ---------
//            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);

            // 手工签收信息NACK 还是------- ok ---------
            channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, false);
        }
    }
}
