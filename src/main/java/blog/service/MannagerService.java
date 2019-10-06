package blog.service;

import blog.service.dto.MannagerDTO;
import io.swagger.models.parameters.QueryParameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

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
    Page<MannagerDTO> findAll(Pageable pageable, MultiValueMap<String,String> multiValueMap);

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
    MannagerDTO delete(Long id,Integer status);

    MannagerDTO count();
}
