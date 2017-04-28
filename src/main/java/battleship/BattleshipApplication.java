package battleship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BattleshipApplication {

	public static void main(String[] args) {
		SpringApplication.run(BattleshipApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository repository) {
		return (args) -> {
			// Save some players
			repository.save(new Player("j.bauer@ctu.gov"));
			repository.save(new Player("c.obrian@ctu.gov"));
			repository.save(new Player("kim_bauer@gmail.com"));
			repository.save(new Player("t.almeida@ctu.gov"));
		};
	}

}
