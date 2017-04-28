package battleship;

import org.springframework.data.annotation.*;

import javax.persistence.*;
import javax.persistence.Id;

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

    public String toString() {
        return userName;
    }

}
