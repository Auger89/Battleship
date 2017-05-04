package battleship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
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

    @RequestMapping("/games")
    public List<Object> getAllGames() {
        return gameRepo.findAll().stream().map(game -> MakeGameDTO(game)).collect(Collectors.toList());
    }

    private Map<String, Object> MakeGameDTO(Game game) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", game.getId());
        dto.put("created", game.getCreationDate());
        dto.put("participations", game.getParticipations().stream()
                .map(participation -> MakeParticipationDTO(participation)).collect(Collectors.toList()));
        return dto;
    }

    private Map<String, Object> MakeParticipationDTO(Participation participation) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", participation.getId());
        dto.put("player", MakePlayerDTO(participation.getPlayer()));
        return dto;
    }

    private Map<String, Object> MakePlayerDTO(Player player) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", player.getId());
        dto.put("email", player.getUserName());
        return dto;
    }
}
