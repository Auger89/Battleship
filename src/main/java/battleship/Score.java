package battleship;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Auger on 17/05/2017.
 * Score Class
 */
@Entity
public class Score {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    private double score;

    private String finishDate;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="player_id")
    private Player player;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="game_id")
    private Game game;

    @OneToMany(mappedBy="score", fetch=FetchType.EAGER)
    private Set<Participation> participations;

    // Constructors
    public Score() {}

    public Score(double score, String finishDate, Participation participation) {
        this.game = participation.getGame();
        this.player = participation.getPlayer();
        this.score = score;
        this.finishDate = finishDate;
    }

    // ToString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Score{");
        sb.append(", ");
        sb.append("id=");
        sb.append(id);
        sb.append(", ");
        sb.append("score=");
        sb.append(score);
        sb.append(", ");
        sb.append("game=");
        sb.append(game);
        sb.append(", ");
        sb.append("player=");
        sb.append(player);
        sb.append(", ");
        sb.append("finishDate=");
        sb.append(finishDate);
        sb.append(", ");
        sb.append("}");

        return sb.toString();
    }

    // Getters & Setters
    public long getId() {
        return id;
    }

    public double getScore() {
        return score;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public Player getPlayer() {
        return player;
    }

    public Game getGame() {
        return game;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
