package com.jsonyao.encryption;

import java.io.IOException;
import java.util.stream.Collectors;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;

import com.alibaba.fastjson.JSON;
import com.dqiangpay.common.api.util.BaseUtil;
import com.dqiangpay.common.global.Global;
import com.dqiangpay.common.order.FundinOrder;

import fi.iki.elonen.NanoHTTPD;

public class DemoHttp extends NanoHTTPD {

    public DemoHttp(int port) {
        super(port);
    }


    public static void main(String[] args) throws IOException {
            new DemoHttp(7777).start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
    }
   
    
    @Override
    public Response serve(IHTTPSession session) {
        StringBuilder sb = new StringBuilder();
        sb.append(session.getMethod().name()).append(session.getUri())
            .append("<br>")
            .append(session.getQueryParameterString())
            .append("<br>")
            .append(session.getHeaders().entrySet().stream().map(entry -> entry.getKey() + " " + entry.getValue()).collect(Collectors.joining("<br>")));
        
        
     //Response resp = newFixedLengthResponse(sb.toString());
     //template   
     ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader("template");
     Configuration cfg;
     try {
         cfg = Configuration.defaultConfiguration();
         GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
         Template t = gt.getTemplate("Demo");
         t.binding("httpMethod", session.getMethod().name());
         t.binding("uri", session.getUri());

         t.binding("headers", session.getHeaders());
         
         String str = t.render();
         //return  newFixedLengthResponse(str);
     } catch (IOException e) {
         e.printStackTrace();
         //return newFixedLengthResponse("unknown error");
     }
        
     //redirect
//     Response resp =   newFixedLengthResponse(Status.REDIRECT, "", "");
//     resp.addHeader("Location", "https:www.baidu.com");
     //DB   
     FundinOrder order = Global.DBTOOLS.runSql(sqlSession -> {
         return sqlSession.selectOne("com.dqiangpay.gateway.order.FundinOrderMapper.findByCriteria", BaseUtil.asMap("orderNo","001"));
     } );

    
        return newFixedLengthResponse(JSON.toJSONString(""));
    }
    
    

}
