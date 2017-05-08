package battleship;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by Auger on 04/05/2017.
 * Repository for Ship
 */
@RepositoryRestResource
public interface ShipRepository extends JpaRepository<Ship, Long> {
}
