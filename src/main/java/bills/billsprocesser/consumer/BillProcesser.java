package bills.billsprocesser.consumer;

import model.avro.Bill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * This class is a consumer that reads from the kafka queue.
 */
public class BillProcesser{

    private static final Logger LOGGER = LoggerFactory.getLogger(BillProcesser.class);

    @KafkaListener(topics = "${kafka.topic.billstopic}")
    public void receive(Bill bill) {
        LOGGER.info("received user='{}'", bill.toString());
    }
}
