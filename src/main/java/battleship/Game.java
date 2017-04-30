package battleship;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.*;

/**
 * Created by Auger on 29/04/2017.
 * many-to-many relation
 */
@Entity
public class Game {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private String creationDate;

    // Constructors
//    public Game() {}

    Game(){
        LocalDateTime now = LocalDateTime.now();
        creationDate =  dtf.format(now);
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public String getCreationDate() {
        return creationDate;
    }

    // setCreationDate not working
    void setCreationDate(long hours) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime modifiedTime = now.plusHours(hours);
        creationDate =  dtf.format(modifiedTime);
    }

    public String toString() {
        return creationDate;
    }

}
