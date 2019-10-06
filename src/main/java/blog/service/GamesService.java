package blog.service;

import blog.service.dto.GamesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Games.
 */
public interface GamesService {

    /**
     * Save a games.
     *
     * @param gamesDTO the entity to save
     * @return the persisted entity
     */
    GamesDTO save(GamesDTO gamesDTO);

    /**
     *  Get all the games.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<GamesDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" games.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    GamesDTO findOne(Long id);

    /**
     *  Delete the "id" games.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
