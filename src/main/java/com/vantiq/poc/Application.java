package com.vantiq.poc;

import com.vantiq.poc.topic.EventPublisher;

public class Application {

    public static void main(String[] args) {
        EventPublisher publisher = new EventPublisher();
        publisher.publish();
    }
}
