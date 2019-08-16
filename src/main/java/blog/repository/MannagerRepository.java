package blog.repository;

import blog.domain.Mannager;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Mannager entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MannagerRepository extends JpaRepository<Mannager, Long> {

}
