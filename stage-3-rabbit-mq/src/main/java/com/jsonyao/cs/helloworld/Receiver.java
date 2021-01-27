package com.jsonyao.cs.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Consumer HelloWorld
 */
public class Receiver extends BaseClient{

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // 1. 创建ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(HOST);
        connectionFactory.setPort(PORT);
        connectionFactory.setVirtualHost(VIRTUAL_HOST);
        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(3000);

        // 2. 创建Connection
        Connection connection = connectionFactory.newConnection();

        // 3. 创建Channel
        Channel channel = connection.createChannel();

        // 4. 创建Queue
        channel.queueDeclare(ROUTING_KEY, false, false, false, null);

        // 5. 创建Consumer
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        channel.basicConsume(ROUTING_KEY, true, queueingConsumer);
        
        // 6. 拉取 | 监听消息
        System.out.println("开始监听消息...");
        while (true){
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("收到消息: " + msg);
        }
    }
}
