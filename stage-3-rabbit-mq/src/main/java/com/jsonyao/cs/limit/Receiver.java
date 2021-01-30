package com.jsonyao.cs.limit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Consumer: 测试消费限流
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

        // 5. 声明队列, 使用默认的交换机
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 7. 创建Consumer
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, false, queueingConsumer);

        // 开启消费限流 报文大小、限流阈值、是否设置为Channel级别
        channel.basicQos(0, 1, false);

        // 8. 拉取消息
        System.err.println("consumer1 start.. ");
        System.out.println("开始拉取消息...");
        while (true){
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("收到消息: " + msg + ", RoutingKey: " + delivery.getEnvelope().getRoutingKey());
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }
}
