package com.jsonyao.cs.helloworld;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * Producer HelloWorld
 */
public class Sender extends BaseClient{

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1. 创建ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(HOST);
        connectionFactory.setPort(PORT);
        connectionFactory.setVirtualHost(VIRTUAL_HOST);

        // 2. 创建Connection
        Connection connection = connectionFactory.newConnection();

        // 3. 创建Channel
        Channel channel = connection.createChannel();

        // 4. 创建Queue
//        channel.queueDeclare(ROUTING_KEY, false, false, false, null);

        // 5. 构建消息
        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                .headers(new HashMap<String, Object>())
                .build();

        // 6. 发送消息到Queue中
        for(int i = 0; i < 5; i++){
            String msg = "Hello World RabbitMQ" + i;
            // 注意, 这里采用了Direct默认的交换机, 且RoutingKey声明为了"test0001", 消费者想要拉取必须拉取名为"test001"的queue
            channel.basicPublish("", ROUTING_KEY, props, msg.getBytes());
            System.out.println("发送消息完毕...");
        }
    }
}
