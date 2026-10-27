package github.biribbo.livetalk;

import io.livekit.server.AccessToken;
import io.livekit.server.RoomJoin;
import io.livekit.server.RoomName;
import io.livekit.server.RoomServiceClient;
import livekit.LivekitModels;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class StreamService {

    private final RoomServiceClient roomServiceClient;
    private final String apiKey;
    private final String apiSecret;

    public StreamService(@Value("${livekit.api.url}") String apiUrl,
                         @Value("${livekit.api.key}") String apiKey,
                         @Value("${livekit.api.secret}") String apiSecret) {
        this.roomServiceClient = RoomServiceClient.createClient(apiUrl, apiKey, apiSecret);
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    public CompletableFuture<LivekitModels.Room> startStream(User streamer, String roomName) {
        if (streamer.isLive()) {
            throw new RuntimeException("only one stream can be hosted at the same time");
        }
        return CallHandler.enqueue(roomServiceClient.createRoom(roomName));
    }

    public CompletableFuture<Void> endStream(String roomName) {
        return CallHandler.enqueue(roomServiceClient.deleteRoom(roomName));
    }

    public String joinStream(User participant, String roomName) {
        AccessToken token = new AccessToken(apiKey, apiSecret);
        token.setName(participant.getUsername());
        token.setIdentity(participant.getId().toString());
        token.addGrants(new RoomJoin(true), new RoomName(roomName));

        return token.toJwt();
    }

    public CompletableFuture<Void> leaveStream(String roomName, String identity) {
        return CallHandler.enqueue(roomServiceClient.removeParticipant(roomName, identity));
    }

    public CompletableFuture<List<LivekitModels.Room>> getAllCurrentStreams() {
        return CallHandler.enqueue(roomServiceClient.listRooms());
    }

    public CompletableFuture<List<LivekitModels.Room>> searchCurrentStreams(String roomName) {
        return CallHandler.enqueue(roomServiceClient.listRooms(List.of(roomName)));
    }
}