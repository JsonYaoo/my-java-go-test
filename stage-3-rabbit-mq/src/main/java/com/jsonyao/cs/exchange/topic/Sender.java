package com.jsonyao.cs.exchange.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Producer: 测试Topic交换机
 */
public class Sender extends BaseClient {

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

        // topic类型交换机: 实际Routing_key
        String routingKey1 = "user.save";
        String routingKey2 = "user.update";
        String routingKey3 = "user.delete.abc";

        // 4. 发送消息: Queue绑定Exchange: 表示交换机EXCHANGE_NAME上ROUTING_KEY的消息会路由到QUEUE_NAME中
        String msg = "Hello World RabbitMQ 4 Topic Exchange Message ... ";
        channel.basicPublish(EXCHANGE_NAME, routingKey1, null, msg.getBytes());
        channel.basicPublish(EXCHANGE_NAME, routingKey2, null, msg.getBytes());
        channel.basicPublish(EXCHANGE_NAME, routingKey3, null, msg.getBytes());
        System.out.println("发送消息完毕...");
    }
}
