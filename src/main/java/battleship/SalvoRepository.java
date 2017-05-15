package battleship;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by Auger on 15/05/2017.
 * Repository for Salvoes
 */
@RepositoryRestResource
public interface SalvoRepository extends JpaRepository<Salvo, Long> {
}
