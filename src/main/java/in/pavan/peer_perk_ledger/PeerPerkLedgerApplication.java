package in.pavan.peer_perk_ledger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@EnableRetry
public class PeerPerkLedgerApplication {
	public static void main(String[] args) {
		SpringApplication.run(PeerPerkLedgerApplication.class, args);
	}
}
