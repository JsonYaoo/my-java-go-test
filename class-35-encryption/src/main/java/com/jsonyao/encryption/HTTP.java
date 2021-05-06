package com.jsonyao.encryption;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response.Status;

/**
 * 见https://github.com/NanoHttpd/nanohttpd
 */
public class Http extends NanoHTTPD {
    private final static Logger logger = LoggerFactory.getLogger(Http.class);
    //key:uri value:controller
    private Map<String, ? extends DQiangpayController> controllers;
    private final boolean allowCrossAccess;


    
    public Http(int port, String root, boolean allowCrossAccess) {
        super(port);
        this.allowCrossAccess = allowCrossAccess;
        //init uri mapping
        Reflections reflections = new Reflections(root);
        Set<Class<? extends DQiangpayController>> clazzs = reflections.getSubTypesOf(DQiangpayController.class);
        controllers = Collections.unmodifiableMap((Map<String, ? extends DQiangpayController>) clazzs.stream().collect(Collectors.toMap((Class<? extends DQiangpayController> clazz) -> {
            return clazz.getName().replaceAll(root, "").replaceAll("\\.", "/");
        }, (clazz) -> {
            try {
                return clazz.newInstance();
            } catch (Exception e) {
                logger.warn(String.format("初始化%s失败，请添加默认无参构造函数", clazz.getName()));
                return null;
            }
        })));

    }

    public static void main(String[] args) throws IOException {
        Http server = new Http(8080, "controller", true);
        server.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        logger.info("dqiangpay gateway server start!");
    }

    /**
     * 总入口，根据规约分发请求
     * 规约： 形如http://host:port/aa/bb/cc/dd?x=v&a=1  其中aa.bb.cc为指定包下的类名，dd为该类的方法
     *       如果未找到aa.bb.cc的类，则按最长匹配找，譬如如果有注册了aa的类，那么就以那个类为controller，bb为方法
     */
    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        String controllerUri = uri.substring(0, uri.lastIndexOf("/"));// https:host:port/[path/path/..path]/end?param1=va1...
        String method = uri.substring(uri.lastIndexOf("/") + 1, uri.length());// https:host:port/path/path/..path/[end]?param1=va1...
        DQiangpayController controller = controllers.get(controllerUri);
       
        if (controller == null) {
        	//如果没有找到精确匹配的url，则尝试找匹配最长前缀的url
        	Map.Entry<String, ? extends DQiangpayController> maxMatchedController = controllers.entrySet().stream().filter(entry->{
        		String registedUrl = entry.getKey();
        		return uri.startsWith(registedUrl);
        	}).max(Comparator.comparing(entry -> (entry.getKey().split("/").length))).get();
        	if (maxMatchedController == null){
        		  logger.warn("unknown uri mapping:" + uri);
                  return newFixedLengthResponse("");//返回空防止恶意扫描
        	}else{
        		controller = maxMatchedController.getValue();
        		String registedUrl = maxMatchedController.getKey();//到类这一层面
        		int beginIndex = registedUrl.length() ;
        		int endIndex = uri.indexOf('/', beginIndex + 1);
        		method = uri.substring(beginIndex + 1, endIndex>0?endIndex:uri.length());//取类的下一段到最近一个
        	}
        }
        logger.debug(String.format("controller:%s method:%s", controller, method));
        Response resp = newFixedLengthResponse(Status.OK, "application/json; charset=utf-8", "");
        try {
            java.lang.reflect.Method m = controller.getClass().getDeclaredMethod(method, IHTTPSession.class);
            String result = (String) m.invoke(controller, session);
            
           
            if(result.trim().startsWith("<!DOCTYPE")){
            	resp =  newFixedLengthResponse(Status.OK, "text/html; charset=utf-8", result);
            }else if(result.trim().startsWith("{") && result.trim().endsWith("}")){
            	resp =  newFixedLengthResponse(Status.OK, "application/json; charset=utf-8", result);
            }else if (result.trim().startsWith("http")) {
            	resp =  newFixedLengthResponse(Status.REDIRECT, "text/plain; charset=utf-8", "");
            	resp.addHeader("Location", result);
            } else{
            	resp =  newFixedLengthResponse(Status.OK, "text/plain; charset=utf-8", result);
            }
            if (allowCrossAccess){
            	resp.addHeader("Access-Control-Allow-Origin", "http://localhost:8082");
            	resp.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH, OPTIONS");
            	resp.addHeader("Access-Control-Allow-Headers", "X-Requested-With, content-type, Authorization");
            	resp.addHeader("Access-Control-Allow-Credentials", "true");
            }
            
            return resp;
        } catch (InvocationTargetException e){ 
        	logger.debug("request error" + ExceptionUtils.getFullStackTrace(e.getTargetException()));
            return newFixedLengthResponse(Status.OK, "application/json; charset=utf-8", String.format("{\"__error__\":\"%s\"}", e.getTargetException().getMessage()));
        } catch (Exception e) {
            logger.debug("request error" + ExceptionUtils.getFullStackTrace(e));
            return newFixedLengthResponse(Status.OK, "application/json; charset=utf-8", String.format("{\"__error__\":\"%s\"}", e + " " + e.getMessage()));
        }
    }
    
    
    
}
