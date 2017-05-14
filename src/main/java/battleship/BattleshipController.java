package battleship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Map;

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

    @RequestMapping("/games")
    public List<Object> getAllGames() {
        return gameRepo.findAll().stream().map(game -> makeGameDTO(game)).collect(Collectors.toList());
    }

    @RequestMapping("/game_view/{participationId}")
    public Map<String, Object> getGameView(@PathVariable long participationId) {
        Participation participation = participationRepo.findOne(participationId);
        Game game = participation.getGame();
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", game.getId());
        dto.put("created", game.getCreationDate());
        dto.put("participations", game.getParticipations().stream()
                .map(part -> makeParticipationDTO(part)).collect(Collectors.toList()));
        dto.put("ships", participation.getShips().stream()
                .map(ship -> makeShipDTO(ship)).collect(Collectors.toList()));

        return dto;
    }

    private Map<String, Object> makeShipDTO(Ship ship) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("type", ship.getType());
        dto.put("location", ship.getLocations());
        return dto;
    }

    private Map<String, Object> makeGameDTO(Game game) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", game.getId());
        dto.put("created", game.getCreationDate());
        dto.put("participations", game.getParticipations().stream()
                .map(participation -> makeParticipationDTO(participation)).collect(Collectors.toList()));
        return dto;
    }

    private Map<String, Object> makeParticipationDTO(Participation participation) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", participation.getId());
        dto.put("player", makePlayerDTO(participation.getPlayer()));
        return dto;
    }

    private Map<String, Object> makePlayerDTO(Player player) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", player.getId());
        dto.put("email", player.getUserName());
        return dto;
    }
}
