package server.main.java.com.example.liveTalk.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.main.java.com.example.liveTalk.model.Stream;
import server.main.java.com.example.liveTalk.model.User;

import java.util.List;

@Repository
public interface StreamRepository extends JpaRepository<Stream, Integer> {
    Stream findById(int id);
    List<Stream> findAllByStreamer(User streamer);
    List<Stream> findAllByStreamerAndLiveIsTrue(User streamer);
    List<Stream> findAllByLiveIsTrue();
    Stream save(Stream stream);
}