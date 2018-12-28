package com.vantiq.poc.topic;

import io.vantiq.client.BaseResponseHandler;
import io.vantiq.client.Vantiq;

import java.util.HashMap;
import java.util.Map;

public class EventPublisher {

    private static Vantiq vantiq;

    public static void main(String[] args) {
        vantiq = new Vantiq(TestRestApi.VANTIQ_URL);
        vantiq.setAccessToken(TestRestApi.TOKEN);
        publish();
    }

    public static void publish() {
        Vantiq vantiq = new Vantiq(TestRestApi.VANTIQ_URL);
        vantiq.setAccessToken(TestRestApi.TOKEN);
        Map event = new HashMap();
        event.put("id", "15");
        event.put("name", "name 15");

        vantiq.publish(Vantiq.SystemResources.TOPICS.value(),
                TestRestApi.TOPIC_PUB,
                event,
                new BaseResponseHandler()
        );
        System.out.println("Publish done!");
    }
}
