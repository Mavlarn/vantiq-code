package com.vantiq.poc.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//import org.apache.kafka.common.protocol.SecurityProtocol;

/**
 * Created by mavlarn on 2017/12/5.
 */
public class KafkaSimpleTest {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaSimpleTest.class);

    private final ObjectMapper om = new ObjectMapper();

    private KafkaConfigService kafkaConfig = new KafkaConfigService();


    @Test
    public void testProducer() throws JsonProcessingException {

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getServer());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "test-kafka");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer producerActivity = new KafkaProducer<>(props);

        Map msg = new HashMap();
        msg.put("key", "mt");
        msg.put("value", 34);

        String value = om.writeValueAsString(msg);
        LOG.debug("Send activity string:{}", value);
        // using customerId as key, it will be used for partition.
        final ProducerRecord<Long, String> record = new ProducerRecord<>(kafkaConfig.VANTIQ_TOPIC, value);
        producerActivity.send(record);
        producerActivity.flush();
    }

    @Test
    public void testConsumer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getServer());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "test-kafka");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        KafkaConsumer consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Lists.newArrayList(kafkaConfig.TOPIC));
        ConsumerRecords recs = consumer.poll(Duration.ofSeconds(10));
        consumer.close();
        LOG.info("read records:{}", recs);
    }

    @Test
    public void testListTopic() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getServer());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaConfig.cliengId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        LOG.info("Topics:" + consumer.listTopics());
        consumer.close();
    }
}
