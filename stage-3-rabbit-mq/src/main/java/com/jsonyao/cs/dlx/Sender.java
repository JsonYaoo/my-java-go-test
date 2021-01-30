package com.jsonyao.cs.dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * Producer: 测试死信队列与TTL队列
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

        // 4. QueueArguments绑定死信队列(交换机)、对应的RoutingKey、TTL队列
        HashMap<String, Object> queueArguments = new HashMap<>();
        queueArguments.put("x-dead-letter-exchange", DLX_EXCHANGE_NAME);
        queueArguments.put("x-dead-letter-routing-key", "123");
        queueArguments.put("x-message-ttl", 6000);

        // 5. 声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE, true, false, false, null);

        // 6. 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, queueArguments);

        // 7. Queue绑定Exchange: 表示交换机EXCHANGE_NAME上ROUTING_KEY的消息会路由到QUEUE_NAME中
        String routingKey = ROUTING_KEY;
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, routingKey);

        // 8. 设置headers
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("my1", 111);

        // 9. 设置BasicProperties
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                // TTL消息
//                .expiration("6000")
                .headers(headers)
                .build();

        // 10. 发送消息
        for(int i = 0; i < 5; i++) {
            String msg = "Hello World RabbitMQ " + i;

            // 使用默认的交换机
            channel.basicPublish("", QUEUE_NAME, properties, msg.getBytes());
            System.out.println("发送消息完毕...");
        }
    }
}
