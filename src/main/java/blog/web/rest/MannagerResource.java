package blog.web.rest;

import com.codahale.metrics.annotation.Timed;
import blog.service.MannagerService;
import blog.web.rest.util.HeaderUtil;
import blog.web.rest.util.PaginationUtil;
import blog.service.dto.MannagerDTO;
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
 * REST controller for managing Mannager.
 */
@RestController
@RequestMapping("/api")
public class MannagerResource {

    private final Logger log = LoggerFactory.getLogger(MannagerResource.class);

    private static final String ENTITY_NAME = "mannager";

    private final MannagerService mannagerService;

    public MannagerResource(MannagerService mannagerService) {
        this.mannagerService = mannagerService;
    }

    /**
     * POST  /mannagers : Create a new mannager.
     *
     * @param mannagerDTO the mannagerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mannagerDTO, or with status 400 (Bad Request) if the mannager has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mannagers")
    @Timed
    public ResponseEntity<MannagerDTO> createMannager(@RequestBody MannagerDTO mannagerDTO) throws URISyntaxException {
        log.debug("REST request to save Mannager : {}", mannagerDTO);
        if (mannagerDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mannager cannot already have an ID")).body(null);
        }
        MannagerDTO result = mannagerService.save(mannagerDTO);
        return ResponseEntity.created(new URI("/api/mannagers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mannagers : Updates an existing mannager.
     *
     * @param mannagerDTO the mannagerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mannagerDTO,
     * or with status 400 (Bad Request) if the mannagerDTO is not valid,
     * or with status 500 (Internal Server Error) if the mannagerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mannagers")
    @Timed
    public ResponseEntity<MannagerDTO> updateMannager(@RequestBody MannagerDTO mannagerDTO) throws URISyntaxException {
        log.debug("REST request to update Mannager : {}", mannagerDTO);
        if (mannagerDTO.getId() == null) {
            return createMannager(mannagerDTO);
        }
        MannagerDTO result = mannagerService.save(mannagerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mannagerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mannagers : get all the mannagers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mannagers in body
     */
    @GetMapping("/mannagers")
    @Timed
    public ResponseEntity<List<MannagerDTO>> getAllMannagers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Mannagers");
        Page<MannagerDTO> page = mannagerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mannagers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mannagers/:id : get the "id" mannager.
     *
     * @param id the id of the mannagerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mannagerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mannagers/{id}")
    @Timed
    public ResponseEntity<MannagerDTO> getMannager(@PathVariable Long id) {
        log.debug("REST request to get Mannager : {}", id);
        MannagerDTO mannagerDTO = mannagerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mannagerDTO));
    }

    /**
     * DELETE  /mannagers/:id : delete the "id" mannager.
     *
     * @param id the id of the mannagerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mannagers/{id}")
    @Timed
    public ResponseEntity<Void> deleteMannager(@PathVariable Long id) {
        log.debug("REST request to delete Mannager : {}", id);
        mannagerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
