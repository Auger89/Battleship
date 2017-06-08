package battleship;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by Auger on 29/04/2017.
 * Repository for Games
 */
@RepositoryRestResource
public interface GameRepository extends JpaRepository<Game, Long> {
    Game findById(long id);
}
