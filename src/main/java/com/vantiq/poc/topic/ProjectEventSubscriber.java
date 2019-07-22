package com.vantiq.poc.topic;

import io.vantiq.client.Vantiq;

public class ProjectEventSubscriber {

    public static void main(String[] args) {
        subscribeNewEvent();
//        subscribeUpdateEvent();
    }

    public static void subscribeNewEvent() {
        Vantiq vantiq = new Vantiq(VantiqTest.VANTIQ_URL);
        vantiq.setAccessToken(VantiqTest.TOKEN);
        vantiq.subscribe(Vantiq.SystemResources.TOPICS.value(),
                VantiqTest.TOPIC_SUB_1,
                null,
                new StandardOutputCallback(VantiqTest.TOPIC_SUB_1)
        );
    }

}
