package com.vantiq.poc.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.security.auth.SecurityProtocol;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

//import org.apache.kafka.common.protocol.SecurityProtocol;

/**
 * Created by mavlarn on 2017/12/5.
 */
public class KafkaTest {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaTest.class);

    private final ObjectMapper om = new ObjectMapper();

    private KafkaConfigService kafkaConfig = new KafkaConfigService();


    @Test
    public void testProducerForVantiq() throws JsonProcessingException {

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getServer());
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
    public void testProducerTas() throws JsonProcessingException {

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getServer());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "TicketActivityProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, SecurityProtocol.SASL_PLAINTEXT.name);
        props.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
        props.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.plain.PlainLoginModule required username=tasproducer password=tV6n9hyHCA;");


        KafkaProducer producerActivity = new KafkaProducer<>(props);

        Map msg = new HashMap();
        msg.put("key", "mt");
        msg.put("value", "test 1");

        String value = om.writeValueAsString(msg);
        LOG.debug("Send activity string:{}", value);
        // using customerId as key, it will be used for partition.
        final ProducerRecord<Long, String> record = new ProducerRecord<>(kafkaConfig.TOPIC, value);
        producerActivity.send(record);
        producerActivity.flush();
    }

    @Test
    public void testConsumerTas() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getServer());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "test-consumer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "tickets-tas");

//        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, SecurityProtocol.SASL_PLAINTEXT.name);
//        props.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
//        props.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.plain.PlainLoginModule required username=tasconsumer password=WdHP9mM6oT;");

        KafkaConsumer consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Lists.newArrayList(kafkaConfig.TOPIC));
        ConsumerRecords recs = consumer.poll(Duration.ofSeconds(10));
        consumer.close();
        LOG.info("read records:{}", recs);
    }

    @Test
    public void testSendMav() throws InterruptedException, ExecutionException, JsonProcessingException {
        Map test = new HashMap();
        test.put("testId", 123);
        test.put("createdDate", ZonedDateTime.now());

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getServer());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "Ticket-Producer");
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 0);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, SecurityProtocol.SASL_PLAINTEXT.name);
        props.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
        props.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.plain.PlainLoginModule required username=mav password=jFq84TJfgN;");

        KafkaProducer producer = new KafkaProducer<>(props);
        Callback callback = (metadata, e) -> {
            if (e != null) {
                LOG.error("Error while sending data", e);
            }
        };

        final ProducerRecord<Long, String> record = new ProducerRecord<>(kafkaConfig.TOPIC, om.writeValueAsString(test));
        Future<RecordMetadata> resultF = producer.send(record, callback);
        LOG.info("send finish：{}", resultF);
        producer.flush();
//        LOG.info("send result:{}", resultF.get());
        producer.close();
    }

    @Test
    public void testReadMav() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getServer());
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, "test-test-consumer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "testgroup");

        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, SecurityProtocol.SASL_PLAINTEXT.name);
        props.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
        props.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.plain.PlainLoginModule required username=mav password=jFq84TJfgN;");

        KafkaConsumer consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Lists.newArrayList(kafkaConfig.TOPIC));

        ConsumerRecords recs = consumer.poll(10000);
        Iterator it = recs.iterator();
        while (it.hasNext()) {
            LOG.info("read records:{}", it.next());
        }
        consumer.close();
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
