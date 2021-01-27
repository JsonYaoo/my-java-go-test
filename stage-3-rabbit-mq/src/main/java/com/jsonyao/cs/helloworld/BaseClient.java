package com.jsonyao.cs.helloworld;

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
     * RoutingKey | QueueName
     */
    public static final String ROUTING_KEY = "test001";

}
