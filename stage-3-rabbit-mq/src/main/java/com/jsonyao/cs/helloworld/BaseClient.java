package com.jsonyao.cs.helloworld;

/**
 * Rabbit MQ客户端常量属性
 */
public class BaseClient {

    /**
     * 主机
     */
//    public static final String HOST = "192.168.1.111";
    public static final String HOST = "192.168.1.112";// Ha-Proxy
//    public static final String HOST = "192.168.1.113";// Ha-Proxy

    /**
     * 端口
     */
//    public static final Integer PORT = 5672;
    public static final Integer PORT = 5671;// Ha-Proxy

    /**
     * 虚拟主机
     */
    public static final String VIRTUAL_HOST = "/";

    /**
     * RoutingKey | QueueName
     */
    public static final String ROUTING_KEY = "test001";

}
