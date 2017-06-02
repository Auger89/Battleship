package battleship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Auger on 02/05/2017.
 * Battleship Controller
 */
@RestController
@RequestMapping("/api")
public class BattleshipController {

    @Autowired
    private GameRepository gameRepo;

    @Autowired
    private ParticipationRepository participationRepo;

    @Autowired
    private PlayerRepository playerRepo;

    // Put scores inside player -> score
    @RequestMapping("/games")
    public Map<String, Object> getPlayerGames(Authentication authentication) {
        Player player = playerRepo.findByUserName(authentication.getName());

        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("player", makePlayerDTO(player));
        dto.put("games", player.getGames().stream().map(game -> makeGameDTO(game)).collect(Collectors.toList()));
        return dto;
    }

    @RequestMapping("/game_view/{participationId}")
    public Map<String, Object> getGameView(@PathVariable long participationId) {
        Participation participation = participationRepo.findOne(participationId);
        Game game = participation.getGame();
        Set<Salvo> allSalvoes = new HashSet<>();

        for (Participation part : game.getParticipations()) {
            for (Salvo salvo : part.getSalvoes()) {
                allSalvoes.add(salvo);
            }
        }
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", game.getId());
        dto.put("created", game.getCreationDate());
        dto.put("participations", game.getParticipations().stream()
                .map(part -> makeParticipationDTO(part)).collect(Collectors.toList()));
        dto.put("ships", participation.getShips().stream()
                .map(ship -> makeShipDTO(ship)).collect(Collectors.toList()));
        dto.put("salvoes", allSalvoes.stream()
                .map(salvo -> makeSalvoDTO(salvo)).collect(Collectors.toList()));
//        dto.put("salvoes2", game.getParticipations().stream()
//                .map(part -> makeSalvoesByPlayerDTO(part)).collect(Collectors.toList()));
//        dto.put("salvoes3", makeSalvoes3DTO(game));
        return dto;
    }

    // The main difference between HashMap and LinkedHashMap is:
    // LinkedHashMap will iterate in the order in which the entries were inserted to the Map,
    // HashMap has no guarantees about the iteration order.
    @RequestMapping("/scores")
    public Map<String, Object> getPlayersScores() {
        List<Player> players = playerRepo.findAll();
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("players", players.stream().map(player -> makePlayerScoreDTO(player)).collect(Collectors.toList()));
        return dto;
    }

    /**
     * This method responds to a request to create a new Player
     * @param username
     * @param password
     * @return ResponseEntity and a JSON
     */
    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createPlayer(@RequestParam("username") String username,
                                                            @RequestParam("password") String password) {
        // Case username field (email) is empty
        EmailValidator emailvalidator = new EmailValidator();
        if (!emailvalidator.validate(username)) {
            return new ResponseEntity<>(makeResponse("error", "invalid email"), HttpStatus.BAD_REQUEST);
        }

        // Case password field is empty
        if (password.length() < 2) {
            return new ResponseEntity<>(makeResponse("error", "invalid password"), HttpStatus.BAD_REQUEST);
        }

        // Case username already exists
        Player user = playerRepo.findByUserName(username);
        if (user != null) {
            return new ResponseEntity<>(makeResponse("error", "name in use"), HttpStatus.FORBIDDEN);
        }

        // Case OK
        playerRepo.save(new Player(username, password));
        return new ResponseEntity<>(makeResponse("username", username), HttpStatus.CREATED);
    }

    private Map<String, Object> makeResponse(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    private Map<String, Object> makePlayerScoreDTO(Player player) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", player.getId());
        dto.put("userName", player.getUserName());
        dto.put("totalScore", player.getTotalScore());
        dto.put("wins", player.getNumberOfWins());
        dto.put("losses", player.getNumberOfLosses());
        dto.put("ties", player.getNumberOfTies());
        return dto;
    }

    private Map<String, Object> makeShipDTO(Ship ship) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("type", ship.getType());
        dto.put("location", ship.getLocations());
        return dto;
    }

    // First Option for salvoes api
    private Map<String, Object> makeSalvoDTO(Salvo salvo) {
        Map<String, Object> dto = new LinkedHashMap<>();
        Participation participation = salvo.getParticipation();
        dto.put("turn", salvo.getTurnNumber());
        dto.put("player", participation.getId());
        dto.put("locations", salvo.getLocations());
        return dto;
    }

    // Option 2 for salvoes api
    private Map<String, Object> makeSalvoesByPlayerDTO(Participation participation) {
        Map<String, Object> dto = new LinkedHashMap<>();
        Player player = participation.getPlayer();
        Set<Salvo> salvoes = participation.getSalvoes();
        dto.put(Objects.toString(player.getId()), makeSalvo2DTO(salvoes));
        return dto;
    }

    // Option 3 for salvoes api
    private Map<String, Object> makeSalvoes3DTO(Game game) {
        Map<String, Object> dto = new LinkedHashMap<>();
        Set<Participation> participations = game.getParticipations();
        for (Participation participation : participations) {
            dto.put(Objects.toString(participation.getId()), makeSalvo2DTO(participation.getSalvoes()));
        }
        return dto;
    }

    private Map<String, Object> makeSalvo2DTO(Set<Salvo> salvoes) {
        Map<String, Object> dto = new LinkedHashMap<>();
        for (Salvo salvo : salvoes) {
            dto.put(Objects.toString(salvo.getTurnNumber()), salvo.getLocations());
        }
        return dto;
    }

    private Map<String, Object> makeGameDTO(Game game) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", game.getId());
        dto.put("created", game.getCreationDate());
        dto.put("participations", game.getParticipations().stream()
                .map(participation -> makeParticipationDTO(participation)).collect(Collectors.toList()));
        return dto;
    }

    private Map<String, Object> makeParticipationDTO(Participation participation) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", participation.getId());
        dto.put("player", makePlayerWithScoreDTO(participation.getPlayer(), participation.getGame()));
        return dto;
    }

    private Map<String, Object> makePlayerWithScoreDTO(Player player, Game game) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", player.getId());
        dto.put("email", player.getUserName());
        Object score;

        score = getScore(player, game);
        dto.put("score", score);

        return dto;
    }

    private Map<String, Object> makePlayerDTO(Player player) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", player.getId());
        dto.put("email", player.getUserName());
        return dto;
    }

    /**
     * This method handles the chance of not getting a Score because a Player hasn't finished a game
     * @param player Player
     * @param game Game
     * @return Score or null
     */
    private Double getScore(Player player, Game game) {
        Score score = player.getScore(game);
        if (score == null) {
            return null;
        } else {
            return score.getScore();
        }
    }

}