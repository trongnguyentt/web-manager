package blog.repository;

import blog.domain.Games;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Games entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GamesRepository extends JpaRepository<Games, Long> {

}
