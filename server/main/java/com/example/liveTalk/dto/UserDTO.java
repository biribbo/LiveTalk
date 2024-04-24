package server.main.java.com.example.liveTalk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import server.main.java.com.example.liveTalk.model.Stream;
import server.main.java.com.example.liveTalk.model.User;

@Getter
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String password;
    @Setter
    private int streamingCounter;
    @Setter
    private boolean currentlyStreaming;
    @Setter
    private Stream currentlyWatching;

    User toUser() {
        return new User(this.username, this.password, this.streamingCounter, this.currentlyStreaming, this.currentlyWatching);
    }
}
