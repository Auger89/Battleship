package battleship;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by Auger on 17/05/2017.
 * Repository for Score
 */
@RepositoryRestResource
public interface ScoreRepository extends JpaRepository<Score, Long>{
}
