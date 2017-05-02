package battleship;

import org.springframework.data.annotation.*;

import javax.persistence.*;
import javax.persistence.Id;
import java.util.Set;
import java.util.List;
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

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                '}';
    }
}
