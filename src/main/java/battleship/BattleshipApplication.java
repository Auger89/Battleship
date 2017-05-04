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
	public CommandLineRunner initData(PlayerRepository playerRepository,
									  GameRepository gameRepository,
									  ParticipationRepository participationRepository) {
		return (args) -> {
			// Create and Save some players
			Player p1 = new Player("j.bauer@ctu.gov");
			Player p2 = new Player("c.obrian@ctu.gov");
			Player p3 = new Player("kim_bauer@gmail.com");
			Player p4 = new Player("t.almeida@ctu.gov");
			playerRepository.save(p1);
			playerRepository.save(p2);
			playerRepository.save(p3);
			playerRepository.save(p4);
			// Creating games
			Game g1 = new Game();
			Game g2 = new Game();
			Game g3 = new Game();
			Game g4 = new Game();
			g2.modifyCreationDate(1);
			g3.modifyCreationDate(2);
			g4.modifyCreationDate(3);
			// Saving games
			gameRepository.save(g1);
			gameRepository.save(g2);
			gameRepository.save(g3);
			gameRepository.save(g4);
			// Creating and saving participations
			Participation part1 = new Participation(p1, g1);
			Participation part2 = new Participation(p2, g1);
			Participation part3 = new Participation(p3, g2);
			Participation part4 = new Participation(p4, g3);
			participationRepository.save(part1);
			participationRepository.save(part2);
			participationRepository.save(part3);
			participationRepository.save(part4);
		};
	}

}
