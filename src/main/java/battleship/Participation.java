package battleship;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

/**
 * Created by Auger on 01/05/2017.
 * Class Participation defines which players are playing which games.
 * Many-to-many relationship with Game and Player.
 */
@Entity
public class Participation {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long Id;

    public String joinDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    private Game game;


    // Constructors
    public Participation() {}

    public Participation(Player player, Game game) {
        this.player = player;
        this.game = game;
        this.joinDate = DateUtil.getDateNow();
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
        return Id;
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

    // Functions

    @Override
    public String toString() {
        return "Participation{" +
                "Id=" + Id +
                ", joinDate='" + joinDate + '\'' +
                ", player=" + player +
                ", game=" + game +
                '}';
    }
}
