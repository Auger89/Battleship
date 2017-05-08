package battleship;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Auger on 04/05/2017.
 * Ship Class
 */
@Entity
public class Ship {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    private String type;

    // Element Collection lets us stablish a OneToMany relationship between Ship and ArrayList<String> locations
    @ElementCollection
    @Column(name="location")
    private List<String> locations;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="participation_id")
    private Participation participation;

    // Constructors
    public Ship() {}

    public Ship(String type, List<String> locations) {
        this.type = type;
        this.locations = locations;
    }

    // ToString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Ship{");
        sb.append("\n");
        sb.append("id=");
        sb.append(id);
        sb.append("\n");
        sb.append("type=");
        sb.append(type);
        sb.append("\n");
        sb.append("locations=");
        sb.append(locations);
        sb.append("\n");
        sb.append("}");

        return sb.toString();
    }

    // Getters & Setters
    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public List<String> getLocations() {
        return locations;
    }

    public Participation getParticipation() {
        return participation;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public void setParticipation(Participation participation) {
        this.participation = participation;
    }
}
