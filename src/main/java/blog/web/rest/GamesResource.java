package blog.web.rest;

import com.codahale.metrics.annotation.Timed;
import blog.service.GamesService;
import blog.web.rest.util.HeaderUtil;
import blog.web.rest.util.PaginationUtil;
import blog.service.dto.GamesDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Games.
 */
@RestController
@RequestMapping("/api")
public class GamesResource {

    private final Logger log = LoggerFactory.getLogger(GamesResource.class);

    private static final String ENTITY_NAME = "games";

    private final GamesService gamesService;

    public GamesResource(GamesService gamesService) {
        this.gamesService = gamesService;
    }

    /**
     * POST  /games : Create a new games.
     *
     * @param gamesDTO the gamesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gamesDTO, or with status 400 (Bad Request) if the games has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/games")
    @Timed
    public ResponseEntity<GamesDTO> createGames(@RequestBody GamesDTO gamesDTO) throws URISyntaxException {
        log.debug("REST request to save Games : {}", gamesDTO);
        if (gamesDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new games cannot already have an ID")).body(null);
        }
        GamesDTO result = gamesService.save(gamesDTO);
        return ResponseEntity.created(new URI("/api/games/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /games : Updates an existing games.
     *
     * @param gamesDTO the gamesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gamesDTO,
     * or with status 400 (Bad Request) if the gamesDTO is not valid,
     * or with status 500 (Internal Server Error) if the gamesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/games")
    @Timed
    public ResponseEntity<GamesDTO> updateGames(@RequestBody GamesDTO gamesDTO) throws URISyntaxException {
        log.debug("REST request to update Games : {}", gamesDTO);
        if (gamesDTO.getId() == null) {
            return createGames(gamesDTO);
        }
        GamesDTO result = gamesService.save(gamesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gamesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /games : get all the games.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of games in body
     */
    @GetMapping("/games")
    @Timed
    public ResponseEntity<List<GamesDTO>> getAllGames(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Games");
        Page<GamesDTO> page = gamesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/games");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /games/:id : get the "id" games.
     *
     * @param id the id of the gamesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gamesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/games/{id}")
    @Timed
    public ResponseEntity<GamesDTO> getGames(@PathVariable Long id) {
        log.debug("REST request to get Games : {}", id);
        GamesDTO gamesDTO = gamesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(gamesDTO));
    }

    /**
     * DELETE  /games/:id : delete the "id" games.
     *
     * @param id the id of the gamesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/games/{id}")
    @Timed
    public ResponseEntity<Void> deleteGames(@PathVariable Long id) {
        log.debug("REST request to delete Games : {}", id);
        gamesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
