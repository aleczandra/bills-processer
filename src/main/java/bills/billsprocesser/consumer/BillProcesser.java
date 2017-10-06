package bills.billsprocesser.consumer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import bills.billsprocesser.config.BillsProcesserConfig;
import bills.billsprocesser.utils.AvroDeserializer;
import bills.billsprocesser.utils.Converter;
import elastic.ElasticClient;
import indexer.ElasticIndexer;
import model.avro.Bill;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.elasticsearch.ElasticsearchWrapperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * This class is a consumer that reads from the kafka queue.
 */
public class BillProcesser implements Runnable {


    @Value("${kafka.topic.billstopic}")
    private String BILLS_TOPIC;

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    private static final Logger LOGGER = LoggerFactory.getLogger(BillProcesser.class);

    @Override
    public void run() {

        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.deserializer", StringDeserializer.class);
        props.put("value.deserializer", AvroDeserializer.class);
        props.put("group.id", "avro");
        props.put("enable.auto.commit", "false");


        Consumer<String, Bill> consumer = new KafkaConsumer<String, Bill>(props);
        consumer.subscribe(Arrays.asList(BILLS_TOPIC));

        try {
            while (true) {
                ConsumerRecords<String, Bill> consumerRecords = consumer.poll(1000);
                for (ConsumerRecord cr : consumerRecords) {
                    LOGGER.info("received user='{}'", cr.value().toString());
                    ElasticIndexer indexer = new ElasticIndexer();
                    try {
                        indexer.indexDocument((Bill)cr.value());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                consumer.commitSync();
            }
        } finally {
            consumer.close();
        }

    }

//
//    @Kafk aListener(topics = "${kafka.topic.billstopic}")
//    public void receive(Bill bill) throws IOException {
//        System.out.println("received user='{}'" + bill.toString());
//        LOGGER.info("received user='{}'", bill.toString());
//        ElasticIndexer indexer = new ElasticIndexer();
//        indexer.indexDocument(bill);
//    }

}
