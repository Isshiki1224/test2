package com.xm.commerce.system.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xm.commerce.security.constant.SecurityConstant;
import com.xm.commerce.security.util.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@Slf4j
@Component
@ServerEndpoint("/uploadTask/{token}")
public class UploadTaskWebSocket {

    static JwtTokenUtils jwtTokenUtils;

    @Autowired
    public void setJwtTokenUtils(JwtTokenUtils jwtTokenUtils) {
        UploadTaskWebSocket.jwtTokenUtils = jwtTokenUtils;
    }

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static final ConcurrentMap<String, UploadTaskWebSocket> webSocketMap = new ConcurrentHashMap<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private String usernameKey;

    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam("token") String token, Session session) {
        session.setMaxIdleTimeout(3600000);
        if (StringUtils.isBlank(token) || !token.startsWith(SecurityConstant.TOKEN_PREFIX)){
            throw new RuntimeException();
        }
        token = token.substring(SecurityConstant.TOKEN_PREFIX.length());
        String username;
        if (jwtTokenUtils.isTokenExpired(token)){
            username = jwtTokenUtils.getUsernameByToken(token);
            this.usernameKey = (username + "--uploadTaskWebSocket--" + LocalDateTime.now().toString());
        }else{
            throw new RuntimeException();
        }
        this.session = session;
        webSocketMap.put(username + "--uploadTaskWebSocket--" + LocalDateTime.now().toString(), this);
        log.info("{} 已连接查看上传任务", username);
        log.info("current connection: {}", this.usernameKey);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketMap.remove(this.usernameKey);//从map中删除
        log.info("disconnect: {}", this.usernameKey);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("来自客户端的消息:" + message);
    }

    /**
     * 发生错误时调用
     *
     * @param error
     */
    @OnError
    public void onError(Throwable error) {
        log.info("发生错误");
        error.printStackTrace();
    }

    /**
     * 把信息传递到session中
     */
    public void sendMessage(Object message, String username) throws Exception {
        Map<String,UploadTaskWebSocket> map =  UploadTaskWebSocket.webSocketMap;
        Set<String> keys = map.keySet();
        ObjectMapper objectMapper = new ObjectMapper();
        for (String key : keys) {
            if (key.startsWith(username)) {
                UploadTaskWebSocket uploadTaskWebSocket = map.get(key);
                if (uploadTaskWebSocket.session.isOpen()){
                    String s = objectMapper.writeValueAsString(message);
                    uploadTaskWebSocket.session.getBasicRemote().sendText(s);
                }
            }
        }
//        log.info("发送给客户端的message: " + message.toString());
//        String s = this.username.get();
//        Map<Object, UploadTaskWebSocket> map = UploadTaskWebSocket.webSocketMap;
//        UploadTaskWebSocket target = map.get(s);
//        if (target != null) {
//            target.session.getBasicRemote().sendObject(message);
//        }
        //this.session.getAsyncRemote().sendText(message);
    }

}
