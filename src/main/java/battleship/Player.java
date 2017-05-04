package battleship;


import javax.persistence.*;
import javax.persistence.Id;
import java.util.Set;
import java.util.List;
import java.lang.String;
import java.lang.StringBuilder;
import static java.util.stream.Collectors.toList;

/**
 * Created by Auger on 27/04/2017.
 * Class Player
 */
@Entity
public class Player {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String userName;

    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    Set<Participation> participations;

    // Constructors
    public Player() {}

    public Player(String userName) {
        this.userName = userName;
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

    // Getters and Setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getId() {
        return id;
    }

    public List<Game> getGames() {
        return participations.stream().map(sub -> sub.getGame()).collect(toList());
    }

}
