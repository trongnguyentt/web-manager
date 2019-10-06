package blog.service.impl;

import blog.service.GamesService;
import blog.domain.Games;
import blog.repository.GamesRepository;
import blog.service.dto.GamesDTO;
import blog.service.mapper.GamesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Games.
 */
@Service
@Transactional
public class GamesServiceImpl implements GamesService{

    private final Logger log = LoggerFactory.getLogger(GamesServiceImpl.class);

    private final GamesRepository gamesRepository;

    private final GamesMapper gamesMapper;

    public GamesServiceImpl(GamesRepository gamesRepository, GamesMapper gamesMapper) {
        this.gamesRepository = gamesRepository;
        this.gamesMapper = gamesMapper;
    }

    /**
     * Save a games.
     *
     * @param gamesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GamesDTO save(GamesDTO gamesDTO) {
        log.debug("Request to save Games : {}", gamesDTO);
        Games games = gamesMapper.toEntity(gamesDTO);
        games = gamesRepository.save(games);
        return gamesMapper.toDto(games);
    }

    /**
     *  Get all the games.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GamesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Games");
        return gamesRepository.findAll(pageable)
            .map(gamesMapper::toDto);
    }

    /**
     *  Get one games by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public GamesDTO findOne(Long id) {
        log.debug("Request to get Games : {}", id);
        Games games = gamesRepository.findOne(id);
        return gamesMapper.toDto(games);
    }

    /**
     *  Delete the  games by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Games : {}", id);
        gamesRepository.delete(id);
    }
}
