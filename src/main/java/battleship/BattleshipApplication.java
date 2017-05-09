package battleship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class BattleshipApplication {

	public static void main(String[] args) {
		SpringApplication.run(BattleshipApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository,
									  GameRepository gameRepository,
									  ParticipationRepository participationRepository,
									  ShipRepository shipRepository) {
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
			// Creating participations
			Participation part1 = new Participation(p1, g1);
			Participation part2 = new Participation(p2, g1);
			Participation part3 = new Participation(p3, g2);
			Participation part4 = new Participation(p4, g3);
			// Creating new ships
			Ship patrolBoat = new Ship("patrol boat", Arrays.asList("H3", "H4"));
			Ship submarine = new Ship("submarine", Arrays.asList("A9", "B9", "C9"));
			Ship destroyer = new Ship("destroyer", Arrays.asList("J7", "J8", "J9"));
			Ship battleShip = new Ship("battleShip", Arrays.asList("C2", "D2", "E2", "F2"));
			Ship carrier = new Ship("carrier", Arrays.asList("B2", "B3", "B4", "B5", "B6"));
			// Adding ships to participations
			part1.addShip(patrolBoat);
			part2.addShip(submarine);
			part3.addShip(destroyer);
			part4.addShip(battleShip);
			part4.addShip(carrier);
			// Saving Participations
			participationRepository.save(part1);
			participationRepository.save(part2);
			participationRepository.save(part3);
			participationRepository.save(part4);
			// Saving ships
			shipRepository.save(patrolBoat);
			shipRepository.save(submarine);
			shipRepository.save(destroyer);
			shipRepository.save(battleShip);
			shipRepository.save(carrier);

		};
	}

}
