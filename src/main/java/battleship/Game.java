package battleship;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Auger on 29/04/2017.
 * many-to-many relation
 */
@Entity
public class Game {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String creationDate;

    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    Set<Participation> participations;

    // Constructors
    public Game() {
        this.creationDate = DateUtil.getDateNow();
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

    // Functions
    public void modifyCreationDate(int hours) {
        creationDate =  DateUtil.getDateNowPlusHours(hours);
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }
}
