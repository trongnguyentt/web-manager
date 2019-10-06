package blog.web.rest;

import blog.WebBlogApp;

import blog.domain.Games;
import blog.repository.GamesRepository;
import blog.service.GamesService;
import blog.service.dto.GamesDTO;
import blog.service.mapper.GamesMapper;
import blog.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GamesResource REST controller.
 *
 * @see GamesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebBlogApp.class)
public class GamesResourceIntTest {

    private static final String DEFAULT_TEN_TAI_KHOAN = "AAAAAAAAAA";
    private static final String UPDATED_TEN_TAI_KHOAN = "BBBBBBBBBB";

    private static final Instant DEFAULT_THOI_GIAN_TAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_THOI_GIAN_TAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_THOI_GIAN_TRUY_CAP_CUOI = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_THOI_GIAN_TRUY_CAP_CUOI = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Autowired
    private GamesRepository gamesRepository;

    @Autowired
    private GamesMapper gamesMapper;

    @Autowired
    private GamesService gamesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGamesMockMvc;

    private Games games;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GamesResource gamesResource = new GamesResource(gamesService);
        this.restGamesMockMvc = MockMvcBuilders.standaloneSetup(gamesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Games createEntity(EntityManager em) {
        Games games = new Games()
            .tenTaiKhoan(DEFAULT_TEN_TAI_KHOAN)
            .thoiGianTao(DEFAULT_THOI_GIAN_TAO)
            .thoiGianTruyCapCuoi(DEFAULT_THOI_GIAN_TRUY_CAP_CUOI)
            .status(DEFAULT_STATUS);
        return games;
    }

    @Before
    public void initTest() {
        games = createEntity(em);
    }

    @Test
    @Transactional
    public void createGames() throws Exception {
        int databaseSizeBeforeCreate = gamesRepository.findAll().size();

        // Create the Games
        GamesDTO gamesDTO = gamesMapper.toDto(games);
        restGamesMockMvc.perform(post("/api/games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gamesDTO)))
            .andExpect(status().isCreated());

        // Validate the Games in the database
        List<Games> gamesList = gamesRepository.findAll();
        assertThat(gamesList).hasSize(databaseSizeBeforeCreate + 1);
        Games testGames = gamesList.get(gamesList.size() - 1);
        assertThat(testGames.getTenTaiKhoan()).isEqualTo(DEFAULT_TEN_TAI_KHOAN);
        assertThat(testGames.getThoiGianTao()).isEqualTo(DEFAULT_THOI_GIAN_TAO);
        assertThat(testGames.getThoiGianTruyCapCuoi()).isEqualTo(DEFAULT_THOI_GIAN_TRUY_CAP_CUOI);
        assertThat(testGames.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createGamesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gamesRepository.findAll().size();

        // Create the Games with an existing ID
        games.setId(1L);
        GamesDTO gamesDTO = gamesMapper.toDto(games);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGamesMockMvc.perform(post("/api/games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gamesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Games in the database
        List<Games> gamesList = gamesRepository.findAll();
        assertThat(gamesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGames() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get all the gamesList
        restGamesMockMvc.perform(get("/api/games?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(games.getId().intValue())))
            .andExpect(jsonPath("$.[*].tenTaiKhoan").value(hasItem(DEFAULT_TEN_TAI_KHOAN.toString())))
            .andExpect(jsonPath("$.[*].thoiGianTao").value(hasItem(DEFAULT_THOI_GIAN_TAO.toString())))
            .andExpect(jsonPath("$.[*].thoiGianTruyCapCuoi").value(hasItem(DEFAULT_THOI_GIAN_TRUY_CAP_CUOI.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getGames() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);

        // Get the games
        restGamesMockMvc.perform(get("/api/games/{id}", games.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(games.getId().intValue()))
            .andExpect(jsonPath("$.tenTaiKhoan").value(DEFAULT_TEN_TAI_KHOAN.toString()))
            .andExpect(jsonPath("$.thoiGianTao").value(DEFAULT_THOI_GIAN_TAO.toString()))
            .andExpect(jsonPath("$.thoiGianTruyCapCuoi").value(DEFAULT_THOI_GIAN_TRUY_CAP_CUOI.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingGames() throws Exception {
        // Get the games
        restGamesMockMvc.perform(get("/api/games/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGames() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);
        int databaseSizeBeforeUpdate = gamesRepository.findAll().size();

        // Update the games
        Games updatedGames = gamesRepository.findOne(games.getId());
        updatedGames
            .tenTaiKhoan(UPDATED_TEN_TAI_KHOAN)
            .thoiGianTao(UPDATED_THOI_GIAN_TAO)
            .thoiGianTruyCapCuoi(UPDATED_THOI_GIAN_TRUY_CAP_CUOI)
            .status(UPDATED_STATUS);
        GamesDTO gamesDTO = gamesMapper.toDto(updatedGames);

        restGamesMockMvc.perform(put("/api/games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gamesDTO)))
            .andExpect(status().isOk());

        // Validate the Games in the database
        List<Games> gamesList = gamesRepository.findAll();
        assertThat(gamesList).hasSize(databaseSizeBeforeUpdate);
        Games testGames = gamesList.get(gamesList.size() - 1);
        assertThat(testGames.getTenTaiKhoan()).isEqualTo(UPDATED_TEN_TAI_KHOAN);
        assertThat(testGames.getThoiGianTao()).isEqualTo(UPDATED_THOI_GIAN_TAO);
        assertThat(testGames.getThoiGianTruyCapCuoi()).isEqualTo(UPDATED_THOI_GIAN_TRUY_CAP_CUOI);
        assertThat(testGames.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingGames() throws Exception {
        int databaseSizeBeforeUpdate = gamesRepository.findAll().size();

        // Create the Games
        GamesDTO gamesDTO = gamesMapper.toDto(games);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGamesMockMvc.perform(put("/api/games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gamesDTO)))
            .andExpect(status().isCreated());

        // Validate the Games in the database
        List<Games> gamesList = gamesRepository.findAll();
        assertThat(gamesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGames() throws Exception {
        // Initialize the database
        gamesRepository.saveAndFlush(games);
        int databaseSizeBeforeDelete = gamesRepository.findAll().size();

        // Get the games
        restGamesMockMvc.perform(delete("/api/games/{id}", games.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Games> gamesList = gamesRepository.findAll();
        assertThat(gamesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Games.class);
        Games games1 = new Games();
        games1.setId(1L);
        Games games2 = new Games();
        games2.setId(games1.getId());
        assertThat(games1).isEqualTo(games2);
        games2.setId(2L);
        assertThat(games1).isNotEqualTo(games2);
        games1.setId(null);
        assertThat(games1).isNotEqualTo(games2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GamesDTO.class);
        GamesDTO gamesDTO1 = new GamesDTO();
        gamesDTO1.setId(1L);
        GamesDTO gamesDTO2 = new GamesDTO();
        assertThat(gamesDTO1).isNotEqualTo(gamesDTO2);
        gamesDTO2.setId(gamesDTO1.getId());
        assertThat(gamesDTO1).isEqualTo(gamesDTO2);
        gamesDTO2.setId(2L);
        assertThat(gamesDTO1).isNotEqualTo(gamesDTO2);
        gamesDTO1.setId(null);
        assertThat(gamesDTO1).isNotEqualTo(gamesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(gamesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(gamesMapper.fromId(null)).isNull();
    }
}
