package blog.service.mapper;

import blog.domain.*;
import blog.service.dto.GamesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Games and its DTO GamesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GamesMapper extends EntityMapper <GamesDTO, Games> {
    
    
    default Games fromId(Long id) {
        if (id == null) {
            return null;
        }
        Games games = new Games();
        games.setId(id);
        return games;
    }
}
