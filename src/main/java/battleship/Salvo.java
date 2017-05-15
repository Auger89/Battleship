package battleship;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Auger on 15/05/2017.
 * Salvo Class
 */
@Entity
public class Salvo {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long Id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="participation_id")
    private Participation participation;

    private int turnNumber;

    @ElementCollection
    @Column(name="locations")
    private List<String> locations;

    // Constructors
    public Salvo() {}

    public Salvo(int turnNumber, List<String> locations) {
        this.turnNumber = turnNumber;
        this.locations = locations;
    }

    // ToString
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//
//        sb.append("Participation{");
//        sb.append("id=");
//        sb.append(id);
//        sb.append(", ");
//        sb.append("participation=");
//        sb.append(participation);
//        sb.append(", ");
//        sb.append("turnNumber=");
//        sb.append(turnNumber);
//        sb.append(", ");
//        sb.append("locations=");
//        sb.append(locations);
//        sb.append("}");
//
//        return sb.toString();
//    }

    // Functions


    // Getters & Setters

    public void setId(long Id) {
        this.Id = Id;
    }

    public long getId() {
        return Id;
    }

    public Participation getParticipation() {
        return participation;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setParticipation(Participation participation) {
        this.participation = participation;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }
}
