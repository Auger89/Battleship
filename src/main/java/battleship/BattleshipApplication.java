package battleship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
									  ShipRepository shipRepository,
									  SalvoRepository salvoRepository,
									  ScoreRepository scoreRepository) {
		return (args) -> {
			// Create and Save some players
			Player p1 = new Player("j.bauer@ctu.gov", "24");
			Player p2 = new Player("c.obrian@ctu.gov", "42");
			Player p3 = new Player("kim_bauer@gmail.com", "kb");
			Player p4 = new Player("t.almeida@ctu.gov", "mole");
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
			Participation part3 = new Participation(p1, g2);
			Participation part4 = new Participation(p2, g2);
			Participation part5 = new Participation(p3, g3);
			Participation part6 = new Participation(p4, g3);
			Participation part7 = new Participation(p4, g4);
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

			Ship d3 = new Ship("destroyer", Arrays.asList("B5", "C5", "D5"));
			Ship pb3 = new Ship("patrol boat", Arrays.asList("C6", "C7"));
			Ship sub3 = new Ship("submarine", Arrays.asList("A2", "A3", "A4"));
			Ship bs3 = new Ship("battleship", Arrays.asList("F2", "G2", "H2", "I2"));
			Ship cr3 = new Ship("carrier", Arrays.asList("H4", "H5", "H6", "H7", "H8"));
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
			part5.addShip(d3);
			part5.addShip(pb3);
			part5.addShip(sub3);
			part5.addShip(bs3);
			part5.addShip(cr3);
			// Creating salvoes
			Salvo s1 = new Salvo(1, Arrays.asList("B2", "C3", "E8"));
			Salvo s2 = new Salvo(2, Arrays.asList("F2", "H4", "A8"));
			Salvo s3 = new Salvo(1, Arrays.asList("B2", "C3", "E8"));
			Salvo s4 = new Salvo(2, Arrays.asList("F2", "H4", "A8"));
			// Adding salvoes
			part1.addSalvo(s1);
			part1.addSalvo(s2);
			part2.addSalvo(s3);
			part2.addSalvo(s4);
			// Adding Scores
			String date1 = DateUtil.getDateNowPlusMins(5);
			String date2 = DateUtil.getDateNowPlusMins(10);
			Score score1 = new Score(1, date1, part1);
			Score score2 = new Score(0, date1, part2);
			Score score3 = new Score(0.5, date2, part3);
			Score score4 = new Score(0.5, date2, part4);
			// Saving Participations
			participationRepository.save(part1);
			participationRepository.save(part2);
			participationRepository.save(part3);
			participationRepository.save(part4);
			participationRepository.save(part5);
			participationRepository.save(part6);
			participationRepository.save(part7);
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
			shipRepository.save(d3);
			shipRepository.save(pb3);
			shipRepository.save(sub3);
			shipRepository.save(bs3);
			shipRepository.save(cr3);
			// Saving salvoes
			salvoRepository.save(s1);
			salvoRepository.save(s2);
			salvoRepository.save(s3);
			salvoRepository.save(s4);
			// Saving Scores
			scoreRepository.save(score1);
			scoreRepository.save(score2);
			scoreRepository.save(score3);
			scoreRepository.save(score4);

		};
	}

}

// Security Authentication Config
@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	PlayerRepository playerRepository;

	@Bean
	UserDetailsService userDetailsService() {
		return new UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				Player player = playerRepository.findByUserName(username);
				if (player != null) {
					return new User(player.getUserName(), player.getPassword(),
							AuthorityUtils.createAuthorityList("USER"));
				} else {
					throw new UsernameNotFoundException("Unknown user: " + username);
				}
			}
		};
	}
}

// Security Authorization Config
@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
				.antMatchers("/web/games.html").permitAll()
				.antMatchers("/web/scripts/**").permitAll()
				.antMatchers("/web/styles/**").permitAll()
				.antMatchers("/web/assets/**").permitAll()
				.antMatchers("/web/sign-up.html").permitAll()
				.antMatchers("/api/scores").permitAll()
				.antMatchers("/api/players").permitAll()
				.antMatchers("/api/login").permitAll()
				.antMatchers("/logout").permitAll()
				.anyRequest().fullyAuthenticated().and().formLogin().loginPage("/api/login");

		// turn off checking for CSRF tokens
		http.csrf().disable();

		// if user is not authenticated, just send an authentication failure response
		http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if login is successful, just clear the flags asking for authentication
		http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

		// if login fails, just send an authentication failure response
		http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if logout is successful, just send a success response
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
	}

	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}
}
