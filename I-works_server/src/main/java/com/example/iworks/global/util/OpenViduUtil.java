package com.example.iworks.global.util;

import com.example.iworks.global.config.OpenViduConfig;
import com.example.iworks.global.config.SecretKeyConfig;
import io.openvidu.java.client.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OpenViduUtil {
    private final OpenVidu openVidu;

    public OpenViduUtil(OpenViduConfig openViduConfig, SecretKeyConfig secretKeyConfig) {
        openVidu = openViduConfig.getOpenVidu();
    }

    public Session createSession() throws OpenViduJavaClientException, OpenViduHttpException {
        System.out.println(openVidu.getActiveSessions());
        for(Session session : openVidu.getActiveSessions()){
            System.out.println(session.getSessionId());
            System.out.println(session.getProperties());
            System.out.println();
        }
        SessionProperties properties = new SessionProperties.Builder().build();
        System.out.println(properties);
        Session session = openVidu.createSession(properties);
        System.out.println("session : "+session);
        return session;
    }

    public String connectSession(String sessionId) throws OpenViduJavaClientException, OpenViduHttpException {
        ConnectionProperties connectionProperties = new ConnectionProperties.Builder()
                .type(ConnectionType.WEBRTC)
                .role(OpenViduRole.PUBLISHER)
                .data("user_data")
                .build();
        System.out.println(openVidu.getActiveSessions());
        Session session = openVidu.getActiveSession(sessionId);;
        System.out.println("session : "+ sessionId);
        Connection connection = session.createConnection();
        System.out.println("token : "+connection.getToken());
        return  connection.getToken();
    }

    public List<Connection> getConnections(String sessionId){
        Session session = openVidu.getActiveSession(sessionId);
        return session.getConnections();
    }
}
