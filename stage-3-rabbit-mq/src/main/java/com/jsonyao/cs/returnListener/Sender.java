package com.jsonyao.cs.returnListener;

import com.rabbitmq.client.*;

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
        String routingKey1 = "abcd.save";
        String routingKey2 = "return.save";
        String routingKey3 = "return.delete.abc";

        // 4. 发送消息: Queue绑定Exchange: 表示交换机EXCHANGE_NAME上ROUTING_KEY的消息会路由到QUEUE_NAME中
        String msg = "Hello World RabbitMQ 4 Return Listener Message ...";

        // 开启Return消息机制
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode,
                                     String replyText,
                                     String exchange,
                                     String routingKey,
                                     AMQP.BasicProperties properties,
                                     byte[] body) throws IOException {
                System.out.println("**************handleReturn**********");
                System.out.println("replyCode: " + replyCode);
                System.out.println("replyText: " + replyText);
                System.out.println("exchange: " + exchange);
                System.out.println("routingKey: " + routingKey);
                System.out.println("body: " + new String(body));
            }
        });
//        channel.basicPublish(EXCHANGE_NAME, routingKey1, true, null, msg.getBytes());
        channel.basicPublish(EXCHANGE_NAME, routingKey1, false, null, msg.getBytes());
//        channel.basicPublish(EXCHANGE_NAME, routingKey2, true, null, msg.getBytes());
        channel.basicPublish(EXCHANGE_NAME, routingKey2, false, null, msg.getBytes());
//        channel.basicPublish(EXCHANGE_NAME, routingKey3, true, null, msg.getBytes());
        channel.basicPublish(EXCHANGE_NAME, routingKey3, false, null, msg.getBytes());
        System.out.println("发送消息完毕...");
    }
}
