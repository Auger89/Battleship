package battleship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.Set;

/**
 * Created by Auger on 29/04/2017.
 * One-to-many relation with Participation and Score
 */
@Entity
public class Game {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String creationDate = DateUtil.getDateNow();

    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    private Set<Participation> participations;

    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    private Set<Score> scores;

    // Constructors
    public Game() {}


    // ToString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Game{");
        sb.append("\n");
        sb.append("id=");
        sb.append(id);
        sb.append("\n");
        sb.append("creationDate=");
        sb.append(creationDate);
        sb.append("\n");
        sb.append("}");

        return sb.toString();
    }

    // Functions
    public void modifyCreationDate(int hours) {
        creationDate =  DateUtil.getDateNowPlusHours(hours);
    }


    // Getters and Setters
    public long getId() {
        return id;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public Set<Score> getScores() {
        return scores;
    }

    @JsonIgnore
    public Set<Participation> getParticipations() {
        return participations;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public void setParticipations(Set<Participation> participations) {
        this.participations = participations;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }
}
