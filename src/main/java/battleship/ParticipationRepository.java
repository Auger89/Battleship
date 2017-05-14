package battleship;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Created by Auger on 02/05/2017.
 * Repository for Participation
 */
public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    Participation findById(long id);
}
