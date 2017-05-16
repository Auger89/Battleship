package battleship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/games")
    public List<Object> getAllGames() {
        return gameRepo.findAll().stream().map(game -> makeGameDTO(game)).collect(Collectors.toList());
    }

    @RequestMapping("/game_view/{participationId}")
    public Map<String, Object> getGameView(@PathVariable long participationId) {
        Participation participation = participationRepo.findOne(participationId);
        Game game = participation.getGame();
        Set<Salvo> allSalvoes = new HashSet<>();
        Set<Participation> participations = game.getParticipations();
        for (Participation part : participations) {
            Set<Salvo> salvoes = part.getSalvoes();
            for (Salvo salvo : salvoes) {
                allSalvoes.add(salvo);
            }
        }
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", game.getId());
        dto.put("created", game.getCreationDate());
        dto.put("participations", game.getParticipations().stream()
                .map(part -> makeParticipationDTO(part)).collect(Collectors.toList()));
        dto.put("ships", participation.getShips().stream()
                .map(ship -> makeShipDTO(ship)).collect(Collectors.toList()));
        dto.put("salvoes", allSalvoes.stream()
                .map(salvo -> makeSalvo2DTO(salvo)).collect(Collectors.toList()));
//        dto.put("salvoes2", game.getParticipations().stream()
//                .map(part -> makeSalvoesByPlayerDTO(part)).collect(Collectors.toList()));
//        dto.put("salvoes3", makeSalvoesDTO(game));
        return dto;
    }

    private Map<String, Object> makeShipDTO(Ship ship) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("type", ship.getType());
        dto.put("location", ship.getLocations());
        return dto;
    }

    // First Option for salvoes api
    private Map<String, Object> makeSalvo2DTO(Salvo salvo) {
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
        dto.put(Objects.toString(player.getId()), makeSalvoDTO(salvoes));
        return dto;
    }

    // Option 3 for salvoes api
    private Map<String, Object> makeSalvoesDTO(Game game) {
        Map<String, Object> dto = new LinkedHashMap<>();
        Set<Participation> participations = game.getParticipations();
        for (Participation participation : participations) {
            dto.put(Objects.toString(participation.getId()), makeSalvoDTO(participation.getSalvoes()));
        }
        return dto;
    }

    private Map<String, Object> makeSalvoDTO(Set<Salvo> salvoes) {
        Map<String, Object> dto = new LinkedHashMap<>();
        for (Salvo salvo : salvoes) {
            dto.put(Objects.toString(salvo.getTurnNumber()), salvo.getLocations());
        }
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
