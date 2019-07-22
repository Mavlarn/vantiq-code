package com.vantiq.poc.kafka;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class KafkaServiceTest {

    @Test
    public void testSendMsg() {
        KafkaProducerService kafkaService = new KafkaProducerService();

        Map msg = new HashMap();
        msg.put("key", "mt");
        msg.put("value", "test 1");
        kafkaService.sendActivity(msg);
    }
}
