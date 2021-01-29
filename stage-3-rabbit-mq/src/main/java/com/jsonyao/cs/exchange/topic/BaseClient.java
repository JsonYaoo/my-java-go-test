package com.jsonyao.cs.exchange.topic;

/**
 * Rabbit MQ客户端常量属性
 */
public class BaseClient {

    /**
     * 主机
     */
    public static final String HOST = "192.168.1.111";

    /**
     * 端口
     */
    public static final Integer PORT = 5672;

    /**
     * 虚拟主机
     */
    public static final String VIRTUAL_HOST = "/";

    /**
     * 交换机名称
     */
    public static final String EXCHANGE_NAME = "test_topic_exchange";

    /**
     * 交换机类型: 直连模式
     */
    public static final String EXCHANGE_TYPE = "topic";

    /**
     * QueueName
     */
    public static final String QUEUE_NAME = "test_topic_queue";

    /**
     * RoutingKey
     */
    public static final String ROUTING_KEY_1 = "user.#";

    /**
     * RoutingKey
     */
    public static final String ROUTING_KEY_2 = "user.*";
}
