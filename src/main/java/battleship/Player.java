package battleship;


import javax.persistence.*;
import javax.persistence.Id;
import java.util.*;
import java.lang.String;
import java.lang.StringBuilder;
import static java.util.stream.Collectors.toList;

/**
 * Created by Auger on 27/04/2017.
 * One-to-Many relation with Participation and Score
 */
@Entity
public class Player {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    private String userName;
    private String password;

    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    private Set<Participation> participations;

    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    private Set<Score> scores;


    // Constructors
    public Player() {}

    public Player(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }


    // ToString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Player{");
        sb.append("\n");
        sb.append("id=");
        sb.append(id);
        sb.append("\n");
        sb.append("userName=");
        sb.append(userName);
        sb.append("\n");
        sb.append("}");

        return sb.toString();
    }


    // Functions
    public Score getScore(Game game) {
        Optional<Score> scoreOptional = scores.stream().filter(score -> score.getGame().equals(game)).findFirst();
        if (scoreOptional.isPresent()) {
            return scoreOptional.get();
        } else {
            return null;
        }
    }

    public double getTotalScore() {
        if (scores == null) {
            return 0;
        } else {
            return scores.stream().map(score -> score.getScore()).mapToDouble(d -> d.doubleValue()).sum();
        }
    }

    public long getNumberOfWins() {
        if (scores == null) {
            return 0;
        } else {
            return scores.stream().map(score -> score.getScore()).filter(s -> s == 1).count();
        }
    }

    public long getNumberOfLosses() {
        if (scores == null) {
            return 0;
        } else {
            return scores.stream().map(score -> score.getScore()).filter(s -> s == 0).count();
        }
    }

    public long getNumberOfTies() {
        if (scores == null) {
            return 0;
        } else {
            return scores.stream().map(score -> score.getScore()).filter(s -> s == 0.5).count();
        }
    }


    // Getters and Setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setParticipations(Set<Participation> participations) {
        this.participations = participations;
    }

    public long getId() {
        return id;
    }

    public List<Game> getGames() {
        return participations.stream().map(sub -> sub.getGame()).collect(toList());
    }

    public Set<Score> getScores() {
        return scores;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
