package battleship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.Set;
//import java.util.List;
//import java.util.stream.Collectors;

/**
 * Created by Auger on 29/04/2017.
 * many-to-many relation
 */
@Entity
public class Game {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String creationDate = DateUtil.getDateNow();

    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    Set<Participation> participations;

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

    @JsonIgnore
    public Set<Participation> getParticipations() {
        return participations;
    }


    // Getters and Setters
    public long getId() {
        return id;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

//    public Set<Player> getPlayers() {
//        return participations.stream().map(participation -> participation.getPlayer()).collect(Collectors.toSet());
//    }

}
