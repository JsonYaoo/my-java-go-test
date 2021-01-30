package com.jsonyao.cs.dlx;

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
    public static final String EXCHANGE_NAME = "test_dlx_exchange";

    /**
     * 交换机类型
     */
    public static final String EXCHANGE_TYPE = "topic";

    /**
     * QueueName
     */
    public static final String QUEUE_NAME = "test_dlx_queue";

    /**
     * RoutingKey
     */
    public static final String ROUTING_KEY = "group.*";

    /**
     * 死信队列交换机名称
     */
    public static final String DLX_EXCHANGE_NAME = "dlx.exchange";

    /**
     * 死信队列交换机类型
     */
    public static final String DLX_EXCHANGE_TYPE = "topic";

    /**
     * 死信队列QueueName
     */
    public static final String DLX_QUEUE_NAME = "dlx.queue";

    /**
     * 死信队列RoutingKey
     */
    public static final String DLX_ROUTING_KEY = "#";
}
