package bills.billsprocesser.consumer;

import java.io.IOException;

import elastic.ElasticClient;
import indexer.ElasticIndexer;
import model.avro.Bill;
import org.elasticsearch.ElasticsearchWrapperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * This class is a consumer that reads from the kafka queue.
 */
public class BillProcesser{

    private static final Logger LOGGER = LoggerFactory.getLogger(BillProcesser.class);

    @KafkaListener(topics = "${kafka.topic.billstopic}")
    public void receive(Bill bill) throws IOException {
        LOGGER.info("received user='{}'", bill.toString());
        ElasticIndexer indexer = new ElasticIndexer();
        indexer.indexDocument(bill);
    }
}
