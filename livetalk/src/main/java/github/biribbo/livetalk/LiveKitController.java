package github.biribbo.livetalk;

import java.util.Map;

import io.livekit.server.*;
import livekit.LivekitWebhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/livekit")
public class LiveKitController {

    @Value("${livekit.api.key}")
    private String LIVEKIT_API_KEY;

    @Value("${livekit.api.secret}")
    private String LIVEKIT_API_SECRET;

    @PostMapping(value = "/token")
    public ResponseEntity<Map<String, String>> createToken(
            @RequestBody Map<String, String> params,
            @AuthenticationPrincipal User user
    ) {
        String roomName = params.get("roomName");

        if (roomName == null || user == null) {
            return ResponseEntity.badRequest().body(Map.of("errorMessage", "Room name is required and user must be logged in"));
        }

        String identity = user.getUsername();

        AccessToken token = new AccessToken(LIVEKIT_API_KEY, LIVEKIT_API_SECRET);
        token.setName(identity);
        token.setIdentity(identity);

        boolean isOwner = roomName.equals(identity);

        VideoGrant grant = new VideoGrant();
        grant.setRoomJoin(true);
        grant.setRoom(roomName);
        grant.setCanPublish(isOwner);
        grant.setCanSubscribe(true);
        token.addGrant(grant);

        return ResponseEntity.ok(Map.of("token", token.toJwt()));
    }

    @PostMapping(value = "/webhook", consumes = "application/webhook+json")
    public ResponseEntity<String> receiveWebhook(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody String body
    ) {
        WebhookReceiver webhookReceiver = new WebhookReceiver(LIVEKIT_API_KEY, LIVEKIT_API_SECRET);
        try {
            // Walidacja podpisu LiveKit (zapobiega fake'owym webhookom)
            LivekitWebhook.WebhookEvent event = webhookReceiver.receive(body, authHeader);

            // Tutaj dodaj logikę biznesową, np:
            if (event.getEvent().equals("room_started")) {
                // userService.updateStreamStatus(event.getRoom().getName(), true);
            }
            if (event.getEvent().equals("room_finished")) {
                // userService.updateStreamStatus(event.getRoom().getName(), false);
            }

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid webhook signature");
        }
        return ResponseEntity.ok("ok");
    }
}