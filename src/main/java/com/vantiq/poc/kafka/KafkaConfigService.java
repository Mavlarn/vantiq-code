package com.vantiq.poc.kafka;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;

/**
 * Created by mavlarn on 2017/12/2.
 */
public class KafkaConfigService {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaConfigService.class);

    private final static String TOPIC_DEV = "topic-dev";
    private final static String TOPIC_PROD = "topic-prod";
    private final static String BOOTSTRAP_SERVERS = "192.168.1.4:9092,192.168.1.5:9092";

    public final static String VANTIQ_TOPIC = "mt_test_topic";

    private final ObjectMapper om = new ObjectMapper();
    String profile = null;
    String TOPIC = null;
    String cliengId = "TestVtqProducer";

    public KafkaConfigService () {
        SimpleModule module = new SimpleModule();
        module.addSerializer(ZonedDateTime.class, JSR310DateTimeSerializer.INSTANCE);
        module.addDeserializer(ZonedDateTime.class, InstantDeserializer.ZONED_DATE_TIME);
        om.registerModule(module);

        TOPIC = TOPIC_DEV;
        LOG.info("Using topic:{}, on kafka server:{}", TOPIC, BOOTSTRAP_SERVERS);
    }

    public String getServer() {
        return BOOTSTRAP_SERVERS;
    }

    public ObjectMapper getOm() {
        return om;
    }

    public String getTOPIC() {
        return TOPIC;
    }

    public String getProfile() {
        return profile;
    }
}
