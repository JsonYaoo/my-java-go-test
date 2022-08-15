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

        // 4. 创建Queue: 生产者创建了消费者就不用创建了
//        channel.queueDeclare(ROUTING_KEY, false, false, false, null);

        // 5. 创建Consumer
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        // 由于采用了默认的交换机(Direct模式), 因此ROUTING_KEY的消息会路由到名为ROUTING_KEY的Queue中, 所以消费者去ROUTING_KEY队列中拿消息
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
