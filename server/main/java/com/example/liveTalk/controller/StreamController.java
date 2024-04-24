package server.main.java.com.example.liveTalk.controller;

import server.main.java.com.example.liveTalk.service.StreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/stream")
public class StreamController {
    final private StreamService service;

    @Autowired
    public StreamController(StreamService service) {
        this.service = service;
    }

}