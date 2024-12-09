package com.example.springkafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    public NewTopic generateTopic() {

        Map<String, String > configs = new HashMap<>();
        configs.put(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_DELETE);
        configs.put(TopicConfig.RETENTION_MS_CONFIG, "172800000");
        configs.put(TopicConfig.RETENTION_BYTES_CONFIG, "100000000");
        configs.put(TopicConfig.MIN_COMPACTION_LAG_MS_CONFIG, "0");
        configs.put(TopicConfig.SEGMENT_MS_CONFIG, "604800000");
        configs.put(TopicConfig.SEGMENT_BYTES_CONFIG, "1073741824");


        return TopicBuilder.name("order-events")
                .partitions(3)
                .replicas(1)
                .configs(configs)
                .build();


    }

}
