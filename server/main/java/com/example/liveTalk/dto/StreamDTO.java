package server.main.java.com.example.liveTalk.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server.main.java.com.example.liveTalk.model.Stream;
import server.main.java.com.example.liveTalk.model.User;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
public class StreamDTO {
    private int id;
    @Setter
    private String title;
    @Setter
    private String description;
    @Setter
    private boolean live;
    private User streamer;
    @Setter
    private Set<User> participants;
    @Setter
    private byte[] audioStream;

    public StreamDTO(String title, String description) {
        this.title = title;
        this.description = description;
        this.live = true;
        this.participants = new HashSet<User>();
    }

    public StreamDTO(Stream source) {
        this.id = source.getId();
        this.title = source.getTitle();
        this.description = source.getDescription();
        this.live = source.isLive();
        this.streamer = source.getStreamer();
        this.participants = source.getParticipants();
    }

    public Stream toStream() {
        return new Stream(this.participants, this.live, this.description, this.title);
    }
}
