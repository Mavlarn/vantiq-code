package com.vantiq.poc.topic;

import io.vantiq.client.BaseResponseHandler;
import io.vantiq.client.Vantiq;

import java.util.HashMap;
import java.util.Map;

public class EventPublisher {

    private static Vantiq vantiq;

    public EventPublisher () {
        vantiq = new Vantiq(VantiqTest.VANTIQ_URL);
        vantiq.setAccessToken(VantiqTest.TOKEN);
    }

    public void publish() {
        Map event = new HashMap();
        event.put("id", "15");
        event.put("name", "name 16");

        vantiq.publish(Vantiq.SystemResources.TOPICS.value(),
                VantiqTest.TOPIC_PUB,
                event,
                new BaseResponseHandler()
        );
        System.out.println("Publish done!");
    }
}
