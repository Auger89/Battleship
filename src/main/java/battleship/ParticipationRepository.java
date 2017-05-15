package battleship;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by Auger on 02/05/2017.
 * Repository for Participation
 */
@RepositoryRestResource
public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    Participation findById(long id);
}
