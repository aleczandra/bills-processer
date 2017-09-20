package bills.billsprocesser.config;

import java.util.HashMap;
import java.util.Map;

import bills.billsprocesser.consumer.BillProcesser;
import bills.billsprocesser.utils.AvroDeserializer;
import model.avro.Bill;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@Configuration
public class BillsProcesserConfig {

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, AvroDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "avro");

        return props;
    }

    @Bean
    public DefaultKafkaConsumerFactory<String, Bill> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(),
            new StringDeserializer(),
            new AvroDeserializer<>(Bill.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Bill> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Bill> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        return factory;
    }

    @Bean
    public BillProcesser receiver() {
        return new BillProcesser();
    }
}



