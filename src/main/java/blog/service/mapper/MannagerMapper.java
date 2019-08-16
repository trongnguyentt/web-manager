package blog.service.mapper;

import blog.domain.*;
import blog.service.dto.MannagerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Mannager and its DTO MannagerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MannagerMapper extends EntityMapper <MannagerDTO, Mannager> {
    
    
    default Mannager fromId(Long id) {
        if (id == null) {
            return null;
        }
        Mannager mannager = new Mannager();
        mannager.setId(id);
        return mannager;
    }
}
