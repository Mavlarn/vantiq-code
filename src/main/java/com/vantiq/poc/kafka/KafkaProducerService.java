package com.vantiq.poc.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Properties;

/**
 * Created by mavlarn on 2017/12/2.
 */
public class KafkaProducerService {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerService.class);

    private KafkaConfigService kafkaConfig = new KafkaConfigService();

    private Producer<Long, String> producer;

    public KafkaProducerService() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getServer());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

//        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, SecurityProtocol.SASL_PLAINTEXT.name);
//        props.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
//        props.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.plain.PlainLoginModule required username=tasproducer password=tV6n9hyHCA;");

        producer = new KafkaProducer<>(props);
    }


    public void sendActivity(Map msg) {
        try {
            String value = kafkaConfig.getOm().writeValueAsString(msg);
            String topic = kafkaConfig.getTOPIC();
            final ProducerRecord<Long, String> record = new ProducerRecord<>(topic, value);
            producer.send(record);
            producer.flush();
        } catch (JsonProcessingException e) {
            LOG.error("Json error for msg:" + msg, e);
        }
    }

    public void close() {
        producer.flush();
        producer.close();
    }

}