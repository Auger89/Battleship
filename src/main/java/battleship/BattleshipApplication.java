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
			Participation part4 = new Participation(p4, g2);
			// Creating new ships
			Ship patrolBoat = new Ship("patrol boat", Arrays.asList("H3", "H4"));
			Ship submarine = new Ship("submarine", Arrays.asList("A9", "B9", "C9"));
			Ship destroyer = new Ship("destroyer", Arrays.asList("J7", "J8", "J9"));
			Ship battleShip = new Ship("battleShip", Arrays.asList("C2", "D2", "E2", "F2"));
			Ship carrier = new Ship("carrier", Arrays.asList("B2", "B3", "B4", "B5", "B6"));
			Ship pb2 = new Ship("patrol boat", Arrays.asList("A2", "A3"));
			Ship sub2 = new Ship("submarine", Arrays.asList("C5", "C6", "C7"));
			Ship d2 = new Ship("destroyer", Arrays.asList("A8", "B8", "C8"));
			Ship bs2 = new Ship("battleShip", Arrays.asList("J7", "J8", "J9", "J10"));
			Ship cr2 = new Ship("carrier", Arrays.asList("D2", "E2", "F2", "G2", "H2"));
			// Adding ships to participations
			part1.addShip(patrolBoat);
			part1.addShip(submarine);
			part1.addShip(destroyer);
			part1.addShip(battleShip);
			part1.addShip(carrier);
			part3.addShip(pb2);
			part3.addShip(sub2);
			part3.addShip(d2);
			part3.addShip(bs2);
			part3.addShip(cr2);
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
			shipRepository.save(pb2);
			shipRepository.save(sub2);
			shipRepository.save(d2);
			shipRepository.save(bs2);
			shipRepository.save(cr2);

		};
	}

}
