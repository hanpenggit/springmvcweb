package cn.hp.websocket.handler;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WebsocketEndPoint extends TextWebSocketHandler {

    /**
     * 将WebSocketSession存入此处，如果申请了多个WebSocketSession，则全部都发送信息
     * 应该申请一个切面来监听  org.springframework.web.socket.handler.LoggingWebSocketHandlerDecorator
     */
    public static List<WebSocketSession> socketList=new ArrayList<>();
    @Override
    protected void handleTextMessage(WebSocketSession session,
                                     TextMessage message) throws Exception {

        int socketLen=socketList.size();
        for(int i=0;i<socketLen;i++){
            if (!socketList.get(i).isOpen()) {
                socketList.remove(i);
                i--;socketLen--;
            }
        }

        TextMessage returnMessage = new TextMessage(message.getPayload()+" received at server");
        if (!socketList.stream().anyMatch(i -> i.getId().equals(session.getId()))) {
            socketList.add(session);
        }
        for(WebSocketSession web:socketList){
            if(web.isOpen()){
                web.sendMessage(returnMessage);
            }
        }
    }
}
