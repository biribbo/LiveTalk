package server.main.java.com.example.liveTalk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import server.main.java.com.example.liveTalk.dto.StreamDTO;
import server.main.java.com.example.liveTalk.model.Stream;
import server.main.java.com.example.liveTalk.model.User;
import server.main.java.com.example.liveTalk.repository.StreamRepository;
import server.main.java.com.example.liveTalk.repository.UserRepository;

import java.util.HashSet;

@Service
public class StreamService {

    private final StreamRepository repository;
    private final UserRepository userRepository;
    private final User currentUser;

    @Autowired
    public StreamService(StreamRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository  = userRepository;
        currentUser = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

    StreamDTO createStream(StreamDTO stream) {
        Stream newStream = stream.toStream();
        currentUser.setCurrentlyStreaming(true);
        int counter = currentUser.getStreamingCounter() + 1;
        currentUser.setStreamingCounter(counter);
        repository.save(newStream);
        userRepository.save(currentUser);
        return new StreamDTO(newStream);
    }

    void endStream(int id) {
        Stream toEnd = repository.findById(id);
        toEnd.setLive(false);
        toEnd.setParticipants(new HashSet<>());
        currentUser.setCurrentlyStreaming(false);
        repository.save(toEnd);
        userRepository.save(currentUser);
    }

    void joinStream(int id) {
        Stream toJoin = repository.findById(id);
        currentUser.setCurrentlyWatching(toJoin);
        userRepository.save(currentUser);
        repository.save(toJoin);
    }

    void leaveStream() {
        Stream toLeave = currentUser.getCurrentlyWatching();
        currentUser.setCurrentlyWatching(null);
        userRepository.save(currentUser);
        repository.save(toLeave);
    }
}
