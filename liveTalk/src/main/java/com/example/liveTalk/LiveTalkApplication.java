package com.example.liveTalk;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.api.stream.ISubscriberStream;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiveTalkApplication extends MultiThreadedApplicationAdapter {
	@Override
	public void streamBroadcastStart(IBroadcastStream stream) {

	}

	@Override
	public void streamBroadcastClose(IBroadcastStream stream) {

	}

	@Override
	public void streamSubscriberStart(ISubscriberStream stream) {

	}

	@Override
	public void streamSubscriberClose(ISubscriberStream stream) {

	}
}