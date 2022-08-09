package com.bluepi.loan.kafka.producer;

import com.bluepi.loan.base.IntegratorConstants;
import com.bluepi.loan.model.MessagePayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;


public class IDFCProducer<K, V> {

    private KafkaProducer<String, String> producer;
    private static volatile IDFCProducer instance;

    private Logger log = LoggerFactory.getLogger(IDFCProducer.class);

    /**
     * private constructor
     */
    private IDFCProducer() {
        Properties props = new Properties();
        props.put(IntegratorConstants.getFromYml.KAFKA_COMMON_SERVER_KEY, IntegratorConstants.getFromYml.KAFKA_COMMON_SERVER_VALUE);
        props.put(IntegratorConstants.KAFKA_KEY_SERIALIZER_KEY, IntegratorConstants.KAFKA_KEY_SERIALIZER_VALUE);
        props.put(IntegratorConstants.KAFKA_VALUE_SERIALIZER_KEY, IntegratorConstants.KAFKA_VALUE_SERIALIZER_VALUE);
        props.put(IntegratorConstants.KAFKA_ACKS_KEY, IntegratorConstants.KAFKA_ACKS_VALUE);
        producer = new KafkaProducer<>(props);
    }

    /**
     * @return singleton object of this class
     */
    public static IDFCProducer getInstance() {

        if (instance == null) {
            synchronized (IDFCProducer.class) {
                if (instance == null)
                    instance = new IDFCProducer();
            }
        }
        return instance;
    }

    /**
     * @return getInstance and  Make singleton from serialize and deserialize operation.
     */
    protected IDFCProducer readResolve() {
        return getInstance();
    }

    /**
     * @param key
     * @param messagePayload
     * @param topicName
     */
    public void sendMessage(@KafkaKey String key, MessagePayload messagePayload, String topicName) throws JsonProcessingException {

        log.info("msg sent to internal update  kafka topic from loan operation kafka producer messagePayload = {} ",  messagePayload);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ProducerRecord<String, String> record = new ProducerRecord<>(topicName, key, objectMapper.writeValueAsString(messagePayload));
            producer.send(record);

        } catch (Exception e) {
            log.error("Exception occurred during sending payload to internal update service from loan operation service {} ",  e);
            throw e;
        }
    }

    /**
     * @param key
     * @param messagePayload
     * @param topicName
     */
    public void sendMessage(@KafkaKey String key, String messagePayload, String topicName) {

        try {

            ProducerRecord<String, String> record = new ProducerRecord<>(topicName, key, messagePayload);
            producer.send(record);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

}
