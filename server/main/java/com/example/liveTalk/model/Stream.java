package server.main.java.com.example.liveTalk.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
public class Stream {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Setter
    private String title;
    @Setter
    private String description;
    @Setter
    private boolean live;
    @ManyToOne
    @JoinColumn(name = "user_name")
    private User streamer;
    @Setter
    @OneToMany
    private Set<User> participants;
    private byte[] audioStream;

    public Stream(Set<User> participants, boolean live, String description, String title) {
        this.participants = participants;
        this.live = live;
        this.description = description;
        this.title = title;
    }
}