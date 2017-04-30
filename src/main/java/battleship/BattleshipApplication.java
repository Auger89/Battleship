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
	public CommandLineRunner initData(GameRepository repository) {
		return (args) -> {
			// Creating games
			Game g1 = new Game();
			Game g2 = new Game();
			Game g3 = new Game();
			g2.setCreationDate(1);
			g3.setCreationDate(2);
			// Saving games
			repository.save(g1);
			repository.save(g2);
			repository.save(g3);
		};
	}

//	public CommandLineRunner initData(PlayerRepository repository) {
//		return (args) -> {
//			// Save some players
//			repository.save(new Player("j.bauer@ctu.gov"));
//			repository.save(new Player("c.obrian@ctu.gov"));
//			repository.save(new Player("kim_bauer@gmail.com"));
//			repository.save(new Player("t.almeida@ctu.gov"));
//		};
//	}

}
