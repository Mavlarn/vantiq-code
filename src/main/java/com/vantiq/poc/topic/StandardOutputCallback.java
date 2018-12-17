package com.vantiq.poc.topic;

import io.vantiq.client.SubscriptionCallback;
import io.vantiq.client.SubscriptionMessage;

public class StandardOutputCallback implements SubscriptionCallback {

    private String topic;

    public StandardOutputCallback(String topic) {
        this.topic = topic;
    }

    public void onConnect() {
        System.out.println(topic + ": Connected Successfully");
    }

    public void onMessage(SubscriptionMessage message) {
        System.out.println(topic + ": Received Message: " + message);
    }

    public void onError(String error) {
        System.out.println(topic + ": Error: " + error);
    }

    public void onFailure(Throwable t) {
        t.printStackTrace();
    }
    
}