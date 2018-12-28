package com.vantiq.poc.topic;

import io.vantiq.client.Vantiq;

public class ProjectEventSubscriber {

    public static void main(String[] args) {
        subscribeNewEvent();
        subscribeUpdateEvent();
    }

    public static void subscribeNewEvent() {
        Vantiq vantiq = new Vantiq(TestRestApi.VANTIQ_URL);
        vantiq.setAccessToken(TestRestApi.TOKEN);
        vantiq.subscribe(Vantiq.SystemResources.TOPICS.value(),
                TestRestApi.TOPIC_SUB_1,
                null,
                new StandardOutputCallback(TestRestApi.TOPIC_SUB_1)
        );
    }

    public static void subscribeUpdateEvent() {
        Vantiq vantiq = new Vantiq(TestRestApi.VANTIQ_URL);
        vantiq.setAccessToken(TestRestApi.TOKEN);
        vantiq.subscribe(Vantiq.SystemResources.TOPICS.value(),
                TestRestApi.TOPIC_SUB_2,
                null,
                new StandardOutputCallback(TestRestApi.TOPIC_SUB_2)
        );
    }
}
