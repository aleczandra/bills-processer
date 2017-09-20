package bills.billsprocesser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class BillsProcesserApplication  {

	public static void main(String[] args) {
		SpringApplication.run(BillsProcesserApplication.class, args);
	}
}
