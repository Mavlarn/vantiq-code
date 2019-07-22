package com.vantiq.poc.topic;

import io.vantiq.client.BaseResponseHandler;
import io.vantiq.client.Vantiq;

import java.util.HashMap;
import java.util.Map;

public class TestMAG {

    private static Vantiq vantiq;

    public static final String VANTIQ_URL = "https://vantiq.maggroupcloud.com";
    public static final String VANTIQ_USER_NAME = "mavlarn";
    public static final String VANTIQ_PASSWORD = "mw800101";
    public static final String TOPIC_PUB = "/serviceA/domainAbc";
    public static final String TOPIC_SUB_1 = "/service1/DomainFoo";
    public static final String TOPIC_SUB_2 = "/service2/DomainBar";
    // 发送到 '/test/topic', 需要在这个测试用户的当前name space里面

    public void init() {
        vantiq = new Vantiq(TestMAG.VANTIQ_URL);
//        vantiq.authenticate(TestMAG.VANTIQ_USER_NAME, TestMAG.VANTIQ_PASSWORD);
        vantiq.setAccessToken("8HVGMfVQdAVv8l273N2bk4Vm0s3_7i7_9eZHeEasFBE=");
        publish();
    }

    public static void publish() {
        Map event = new HashMap();
        event.put("id", "15");
        event.put("name", "name 15");

        vantiq.publish(Vantiq.SystemResources.TOPICS.value(),
                VantiqTest.TOPIC_PUB,
                event,
                new BaseResponseHandler()
        );
        System.out.println("Publish done!");
    }
}
