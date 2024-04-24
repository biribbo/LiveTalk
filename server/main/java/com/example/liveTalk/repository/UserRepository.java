package server.main.java.com.example.liveTalk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.main.java.com.example.liveTalk.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
    List<User> findByCurrentlyStreamingIsTrue();
    User save (User user);
}
