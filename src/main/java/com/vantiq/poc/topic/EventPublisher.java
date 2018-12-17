package com.vantiq.poc.topic;

import io.vantiq.client.Vantiq;
import org.json.JSONObject;

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
        Map event = new HashMap();
        event.put("EVENT_KEY", "NEW_Project");
        event.put("Service_Token", "some  token");
        event.put("User_ID", "23456789");
        JSONObject data = new JSONObject();
        data.put("ProjectID", "m1101");
        data.put("name", "项目名字");
        data.put("prop1", "属性值");
        data.put("value1", 12.6);
        event.put("data", data);

        vantiq.publish(Vantiq.SystemResources.TOPICS.value(),
                "/mt/capitalheat/service1/project/update",
                event,
                new BasicResponseHandler( //callback to display results
                        "Published to topic /mt/capitalheat/service1/project/new", BasicResponseHandler.DISPLAY_NONE)
        );
        System.out.println("Publish done!");
    }
}
