package com.jsonyao.cs.limit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Producer: 测试消费限流
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

        Map<String, Object> headers = new HashMap<String, Object>();
        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                .headers(headers).build();

        // 4. 发送消息: Queue绑定Exchange: 表示交换机EXCHANGE_NAME上ROUTING_KEY的消息会路由到QUEUE_NAME中
        for(int i = 0; i < 5;i++) {
            String msg = "Hello World RabbitMQ " + i;

            // 使用默认的交换机
            channel.basicPublish("", QUEUE_NAME, props, msg.getBytes());
            System.out.println("发送消息完毕...");
        }
    }
}
