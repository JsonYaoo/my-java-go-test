package com.jsonyao.cs.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Consumer: 测试死信队列与TTL队列
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

        // 4. 声明死信队列交换机
        channel.exchangeDeclare(DLX_EXCHANGE_NAME, DLX_EXCHANGE_TYPE, true, false, false,null);

        // 5. 声明死信队列
        channel.queueDeclare(DLX_QUEUE_NAME, false, false, false, null);

        // 6. Queue绑定Exchange: 表示交换机EXCHANGE_NAME上ROUTING_KEY的消息会路由到QUEUE_NAME中
        channel.queueBind(DLX_QUEUE_NAME, DLX_EXCHANGE_NAME, DLX_ROUTING_KEY);

        // 7. 创建Consumer
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        channel.basicConsume(DLX_QUEUE_NAME, false, queueingConsumer);

        // 8. 拉取消息
        System.err.println("consumer1 start.. ");
        System.out.println("开始拉取死信队列消息...");
        while (true){
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("收到死信队列消息: " + msg + ", RoutingKey: " + delivery.getEnvelope().getRoutingKey());

            Map<String, Object> headers = delivery.getProperties().getHeaders();
            System.out.println("headers get my1 value: " + headers.get("my1"));

            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }
}
