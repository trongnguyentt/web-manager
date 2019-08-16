package blog.service;

import blog.service.dto.MannagerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Mannager.
 */
public interface MannagerService {

    /**
     * Save a mannager.
     *
     * @param mannagerDTO the entity to save
     * @return the persisted entity
     */
    MannagerDTO save(MannagerDTO mannagerDTO);

    /**
     *  Get all the mannagers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MannagerDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" mannager.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MannagerDTO findOne(Long id);

    /**
     *  Delete the "id" mannager.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
