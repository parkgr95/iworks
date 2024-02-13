package com.example.iworks.global.util;

import io.openvidu.java.client.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OpenViduUtil {
    private final OpenVidu openVidu;


    public OpenViduUtil(@Value("${openvidu.host}")String OPENVIDU_SERVER_URL, @Value("${openvidu.secret}") String SECRET_KEY) {
        this.openVidu = new OpenVidu(OPENVIDU_SERVER_URL,SECRET_KEY);
    }

    public String createSession() throws OpenViduJavaClientException, OpenViduHttpException {
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
        return session.getSessionId();
    }

    public Session createSession(String sessionId) throws OpenViduJavaClientException, OpenViduHttpException {
        SessionProperties properties = new SessionProperties.Builder().customSessionId(sessionId).build();
        Session session = openVidu.createSession(properties);
        return session;
    }

    public String connectSession(String sessionId) throws OpenViduJavaClientException, OpenViduHttpException {
        ConnectionProperties connectionProperties = new ConnectionProperties.Builder()
                .type(ConnectionType.WEBRTC)
                .role(OpenViduRole.PUBLISHER)
                .data("user_data")
                .build();
        System.out.println(openVidu.getActiveSessions());
        Session session = openVidu.getActiveSession(sessionId);
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
