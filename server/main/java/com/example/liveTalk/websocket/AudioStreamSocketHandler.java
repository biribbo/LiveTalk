package server.main.java.com.example.liveTalk.websocket;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;
import server.main.java.com.example.liveTalk.service.StreamService;

@Component
public class AudioStreamHandler extends BinaryWebSocketHandler {

    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final StreamService streamService;

    @Autowired
    public AudioStreamHandler(StreamService streamService) {
        this.streamService = streamService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws IOException {
        byte[] audioData = message.getPayload().array();
        int streamId = getSessionStreamId(session);
        //streamService.updateAudioStream(streamId, audioData);
    }

    private int getSessionStreamId(WebSocketSession session) {
        Integer streamId = (Integer) session.getAttributes().get("streamId");
        return (streamId != null) ? streamId : -1;
    }
}

