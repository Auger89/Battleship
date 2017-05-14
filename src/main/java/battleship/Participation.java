package battleship;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Auger on 01/05/2017.
 * Class Participation defines which players are playing which games.
 * Many-to-many relationship with Game and Player.
 */
@Entity
public class Participation {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    private String joinDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    private Game game;

    // mappedBy is the field's name that controls the relationship
    @OneToMany(mappedBy="participation", fetch=FetchType.EAGER)
    private Set<Ship> ships = new HashSet<>();

    // Constructors
    public Participation() {}

    public Participation(Player player, Game game) {
        this.player = player;
        this.game = game;
        this.joinDate = DateUtil.getDateNow();
    }


    // ToString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Participation{");
        sb.append("id=");
        sb.append(id);
        sb.append(", ");
        sb.append("joinDate=");
        sb.append(joinDate);
        sb.append(", ");
        sb.append("player=");
        sb.append(player);
        sb.append(", ");
        sb.append("game=");
        sb.append(game);
        sb.append(", ");
        sb.append("ships=");
        sb.append(ships);
        sb.append("}");

        return sb.toString();
    }

    // Functions
    public void addShip(Ship ship) {
        this.ships.add(ship);
        ship.setParticipation(this);
    }


    // Getters and Setters
    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public long getId() {
        return id;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public Player getPlayer() {
        return player;
    }

    public Game getGame() {
        return game;
    }

    public Set<Ship> getShips() {
        return ships;
    }

}
