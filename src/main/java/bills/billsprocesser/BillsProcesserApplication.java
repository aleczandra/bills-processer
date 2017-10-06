package bills.billsprocesser;

import bills.billsprocesser.consumer.BillProcesser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BillsProcesserApplication implements CommandLineRunner {


	@Autowired
	BillProcesser billProcesser;

	@Override
	public void run(String... strings) throws Exception {
		Thread p1 = new Thread(billProcesser,"Processer");
		p1.start();
	}

	public static void main(String[] args) {
		SpringApplication.run(BillsProcesserApplication.class, args);
	}
}
