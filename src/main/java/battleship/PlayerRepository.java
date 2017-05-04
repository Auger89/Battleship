package battleship;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by Auger on 28/04/2017.
 * Repository for Player
 */
@RepositoryRestResource
public interface PlayerRepository extends JpaRepository<Player, Long> {
}
