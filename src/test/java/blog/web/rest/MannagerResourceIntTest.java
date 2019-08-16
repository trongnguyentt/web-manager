package blog.web.rest;

import blog.WebBlogApp;

import blog.domain.Mannager;
import blog.repository.MannagerRepository;
import blog.service.MannagerService;
import blog.service.dto.MannagerDTO;
import blog.service.mapper.MannagerMapper;
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
 * Test class for the MannagerResource REST controller.
 *
 * @see MannagerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebBlogApp.class)
public class MannagerResourceIntTest {

    private static final String DEFAULT_SPENT_MONEY = "AAAAAAAAAA";
    private static final String UPDATED_SPENT_MONEY = "BBBBBBBBBB";

    private static final String DEFAULT_SPENT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_SPENT_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Autowired
    private MannagerRepository mannagerRepository;

    @Autowired
    private MannagerMapper mannagerMapper;

    @Autowired
    private MannagerService mannagerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMannagerMockMvc;

    private Mannager mannager;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MannagerResource mannagerResource = new MannagerResource(mannagerService);
        this.restMannagerMockMvc = MockMvcBuilders.standaloneSetup(mannagerResource)
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
    public static Mannager createEntity(EntityManager em) {
        Mannager mannager = new Mannager()
            .spentMoney(DEFAULT_SPENT_MONEY)
            .spentContent(DEFAULT_SPENT_CONTENT)
            .status(DEFAULT_STATUS);
        return mannager;
    }

    @Before
    public void initTest() {
        mannager = createEntity(em);
    }

    @Test
    @Transactional
    public void createMannager() throws Exception {
        int databaseSizeBeforeCreate = mannagerRepository.findAll().size();

        // Create the Mannager
        MannagerDTO mannagerDTO = mannagerMapper.toDto(mannager);
        restMannagerMockMvc.perform(post("/api/mannagers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mannagerDTO)))
            .andExpect(status().isCreated());

        // Validate the Mannager in the database
        List<Mannager> mannagerList = mannagerRepository.findAll();
        assertThat(mannagerList).hasSize(databaseSizeBeforeCreate + 1);
        Mannager testMannager = mannagerList.get(mannagerList.size() - 1);
        assertThat(testMannager.getSpentMoney()).isEqualTo(DEFAULT_SPENT_MONEY);
        assertThat(testMannager.getSpentContent()).isEqualTo(DEFAULT_SPENT_CONTENT);
        assertThat(testMannager.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMannager.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testMannager.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createMannagerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mannagerRepository.findAll().size();

        // Create the Mannager with an existing ID
        mannager.setId(1L);
        MannagerDTO mannagerDTO = mannagerMapper.toDto(mannager);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMannagerMockMvc.perform(post("/api/mannagers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mannagerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Mannager in the database
        List<Mannager> mannagerList = mannagerRepository.findAll();
        assertThat(mannagerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMannagers() throws Exception {
        // Initialize the database
        mannagerRepository.saveAndFlush(mannager);

        // Get all the mannagerList
        restMannagerMockMvc.perform(get("/api/mannagers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mannager.getId().intValue())))
            .andExpect(jsonPath("$.[*].spentMoney").value(hasItem(DEFAULT_SPENT_MONEY.toString())))
            .andExpect(jsonPath("$.[*].spentContent").value(hasItem(DEFAULT_SPENT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getMannager() throws Exception {
        // Initialize the database
        mannagerRepository.saveAndFlush(mannager);

        // Get the mannager
        restMannagerMockMvc.perform(get("/api/mannagers/{id}", mannager.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mannager.getId().intValue()))
            .andExpect(jsonPath("$.spentMoney").value(DEFAULT_SPENT_MONEY.toString()))
            .andExpect(jsonPath("$.spentContent").value(DEFAULT_SPENT_CONTENT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingMannager() throws Exception {
        // Get the mannager
        restMannagerMockMvc.perform(get("/api/mannagers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMannager() throws Exception {
        // Initialize the database
        mannagerRepository.saveAndFlush(mannager);
        int databaseSizeBeforeUpdate = mannagerRepository.findAll().size();

        // Update the mannager
        Mannager updatedMannager = mannagerRepository.findOne(mannager.getId());
        updatedMannager
            .spentMoney(UPDATED_SPENT_MONEY)
            .spentContent(UPDATED_SPENT_CONTENT)
            .status(UPDATED_STATUS);
        MannagerDTO mannagerDTO = mannagerMapper.toDto(updatedMannager);

        restMannagerMockMvc.perform(put("/api/mannagers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mannagerDTO)))
            .andExpect(status().isOk());

        // Validate the Mannager in the database
        List<Mannager> mannagerList = mannagerRepository.findAll();
        assertThat(mannagerList).hasSize(databaseSizeBeforeUpdate);
        Mannager testMannager = mannagerList.get(mannagerList.size() - 1);
        assertThat(testMannager.getSpentMoney()).isEqualTo(UPDATED_SPENT_MONEY);
        assertThat(testMannager.getSpentContent()).isEqualTo(UPDATED_SPENT_CONTENT);
        assertThat(testMannager.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMannager.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testMannager.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingMannager() throws Exception {
        int databaseSizeBeforeUpdate = mannagerRepository.findAll().size();

        // Create the Mannager
        MannagerDTO mannagerDTO = mannagerMapper.toDto(mannager);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMannagerMockMvc.perform(put("/api/mannagers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mannagerDTO)))
            .andExpect(status().isCreated());

        // Validate the Mannager in the database
        List<Mannager> mannagerList = mannagerRepository.findAll();
        assertThat(mannagerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMannager() throws Exception {
        // Initialize the database
        mannagerRepository.saveAndFlush(mannager);
        int databaseSizeBeforeDelete = mannagerRepository.findAll().size();

        // Get the mannager
        restMannagerMockMvc.perform(delete("/api/mannagers/{id}", mannager.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Mannager> mannagerList = mannagerRepository.findAll();
        assertThat(mannagerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mannager.class);
        Mannager mannager1 = new Mannager();
        mannager1.setId(1L);
        Mannager mannager2 = new Mannager();
        mannager2.setId(mannager1.getId());
        assertThat(mannager1).isEqualTo(mannager2);
        mannager2.setId(2L);
        assertThat(mannager1).isNotEqualTo(mannager2);
        mannager1.setId(null);
        assertThat(mannager1).isNotEqualTo(mannager2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MannagerDTO.class);
        MannagerDTO mannagerDTO1 = new MannagerDTO();
        mannagerDTO1.setId(1L);
        MannagerDTO mannagerDTO2 = new MannagerDTO();
        assertThat(mannagerDTO1).isNotEqualTo(mannagerDTO2);
        mannagerDTO2.setId(mannagerDTO1.getId());
        assertThat(mannagerDTO1).isEqualTo(mannagerDTO2);
        mannagerDTO2.setId(2L);
        assertThat(mannagerDTO1).isNotEqualTo(mannagerDTO2);
        mannagerDTO1.setId(null);
        assertThat(mannagerDTO1).isNotEqualTo(mannagerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mannagerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mannagerMapper.fromId(null)).isNull();
    }
}
