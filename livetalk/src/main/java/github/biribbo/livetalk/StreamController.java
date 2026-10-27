package github.biribbo.livetalk;

import livekit.LivekitModels;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/streams")
public class StreamController {

    private final StreamService streamService;

    public StreamController(StreamService streamService) {
        this.streamService = streamService;
    }

    @PostMapping("/{roomName}")
    public CompletableFuture<ResponseEntity<LivekitModels.Room>> startStream(
            @AuthenticationPrincipal User streamer,
            @PathVariable String roomName) {
        return streamService.startStream(streamer, roomName)
                .thenApply(ResponseEntity::ok);
    }

    @DeleteMapping("/{roomName}")
    public CompletableFuture<ResponseEntity<Void>> endStream(@PathVariable String roomName) {
        return streamService.endStream(roomName)
                .thenApply(v -> ResponseEntity.noContent().build());
    }

    @PostMapping("/{roomName}/join")
    public ResponseEntity<String> joinStream(
            @AuthenticationPrincipal User participant,
            @PathVariable String roomName) {
        String token = streamService.joinStream(participant, roomName);
        return ResponseEntity.ok(token);
    }

    @DeleteMapping("/{roomName}/participants/{identity}")
    public CompletableFuture<ResponseEntity<Void>> leaveStream(
            @PathVariable String roomName,
            @PathVariable String identity) {
        return streamService.leaveStream(roomName, identity)
                .thenApply(v -> ResponseEntity.noContent().build());
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<LivekitModels.Room>>> getStreams(
            @RequestParam(required = false) String roomName) {
        if (roomName != null && !roomName.isBlank()) {
            return streamService.searchCurrentStreams(roomName)
                    .thenApply(ResponseEntity::ok);
        }
        return streamService.getAllCurrentStreams()
                .thenApply(ResponseEntity::ok);
    }
}