package cn.hp.websocket.handler;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

public class WebsocketEndPoint extends TextWebSocketHandler {


    /**
     * 将WebSocketSession存入此处，如果申请了多个WebSocketSession，则全部都发送信息
     */
    public static List<WebSocketSession> socketList=new ArrayList<>();
    @Override
    protected void handleTextMessage(WebSocketSession session,
                                     TextMessage message) throws Exception {
        String msg=new String(message.asBytes())+"   |"+socketList.size();

        sendMessageToAll(new TextMessage(msg));
    }

    /**
     * 单独发给某一个人(WebSocketSession)
     * @param session
     * @param message
     * @throws IOException
     */
    public void sendMessageToSomeone(WebSocketSession session, TextMessage message) throws IOException {
        session.sendMessage(message);
    }

    /**
     * 发送给所有的WebSocketSession
     * @param message
     * @throws IOException
     */
    public void sendMessageToAll(TextMessage message) throws IOException {
        for(WebSocketSession session:socketList){
            session.sendMessage(message);
        }
    }





    /**
     * websocket连接创建之后调用
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        socketList.add(session);
    }
    /**
     * websocket连接关闭之后调用
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        socketList.remove(session);
    }
    /**
     * websocket连接发生异常之后调用
     * @param session
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
        if(session.isOpen()){
            session.close();
        }
        socketList.remove(session);
    }
}
