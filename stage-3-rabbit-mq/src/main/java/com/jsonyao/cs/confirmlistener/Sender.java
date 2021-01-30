package com.jsonyao.cs.confirmlistener;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Producer: 测试Fanout交换机
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
        String routingKey1 = "confirm.save";

        // 4. 发送消息: Queue绑定Exchange: 表示交换机EXCHANGE_NAME上ROUTING_KEY的消息会路由到QUEUE_NAME中
        String msg = "Hello World RabbitMQ 4 Confirm Listener Message ...";
        channel.confirmSelect();// 开启Confirm消息机制
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("------- ok ---------" + deliveryTag);
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.err.println("------- error ---------" + deliveryTag);
            }
        });
        channel.basicPublish(EXCHANGE_NAME, routingKey1, null, msg.getBytes());
        System.out.println("发送消息完毕...");
    }
}
