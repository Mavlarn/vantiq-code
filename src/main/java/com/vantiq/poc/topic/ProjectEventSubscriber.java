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
                "/mt/capitalheat/service1/project/new",
                null,
                new StandardOutputCallback("/mt/capitalheat/service1/project/new")
        );
    }

    public static void subscribeUpdateEvent() {
        Vantiq vantiq = new Vantiq(TestRestApi.VANTIQ_URL);
        vantiq.setAccessToken(TestRestApi.TOKEN);
        vantiq.subscribe(Vantiq.SystemResources.TOPICS.value(),
                "/mt/capitalheat/service1/project/update",
                null,
                new StandardOutputCallback("/mt/capitalheat/service1/project/update")
        );
    }
}
