package org.bookshare.web.rest;

import org.bookshare.BookshareApp;
import org.bookshare.domain.Goodreads;
import org.bookshare.repository.GoodreadsRepository;
import org.bookshare.service.GoodreadsService;
import org.bookshare.service.dto.GoodreadsDTO;
import org.bookshare.service.mapper.GoodreadsMapper;
import org.bookshare.web.rest.errors.ExceptionTranslator;
import org.bookshare.service.dto.GoodreadsCriteria;
import org.bookshare.service.GoodreadsQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.bookshare.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GoodreadsResource} REST controller.
 */
@SpringBootTest(classes = BookshareApp.class)
public class GoodreadsResourceIT {

    private static final Integer DEFAULT_BOOK_ID = 1;
    private static final Integer UPDATED_BOOK_ID = 2;
    private static final Integer SMALLER_BOOK_ID = 1 - 1;

    private static final Integer DEFAULT_G_R_BOOK_ID = 1;
    private static final Integer UPDATED_G_R_BOOK_ID = 2;
    private static final Integer SMALLER_G_R_BOOK_ID = 1 - 1;

    private static final Integer DEFAULT_RATING_COUNT = 1;
    private static final Integer UPDATED_RATING_COUNT = 2;
    private static final Integer SMALLER_RATING_COUNT = 1 - 1;

    private static final Integer DEFAULT_TEXT_REVIEWS_COUNT = 1;
    private static final Integer UPDATED_TEXT_REVIEWS_COUNT = 2;
    private static final Integer SMALLER_TEXT_REVIEWS_COUNT = 1 - 1;

    private static final Float DEFAULT_AVERAGE_RATING = 1F;
    private static final Float UPDATED_AVERAGE_RATING = 2F;
    private static final Float SMALLER_AVERAGE_RATING = 1F - 1F;

    private static final LocalDate DEFAULT_LAST_UPDATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_UPDATED = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_LAST_UPDATED = LocalDate.ofEpochDay(-1L);

    @Autowired
    private GoodreadsRepository goodreadsRepository;

    @Autowired
    private GoodreadsMapper goodreadsMapper;

    @Autowired
    private GoodreadsService goodreadsService;

    @Autowired
    private GoodreadsQueryService goodreadsQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restGoodreadsMockMvc;

    private Goodreads goodreads;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GoodreadsResource goodreadsResource = new GoodreadsResource(goodreadsService, goodreadsQueryService);
        this.restGoodreadsMockMvc = MockMvcBuilders.standaloneSetup(goodreadsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Goodreads createEntity(EntityManager em) {
        Goodreads goodreads = new Goodreads()
            .bookId(DEFAULT_BOOK_ID)
            .gRBookId(DEFAULT_G_R_BOOK_ID)
            .ratingCount(DEFAULT_RATING_COUNT)
            .textReviewsCount(DEFAULT_TEXT_REVIEWS_COUNT)
            .averageRating(DEFAULT_AVERAGE_RATING)
            .lastUpdated(DEFAULT_LAST_UPDATED);
        return goodreads;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Goodreads createUpdatedEntity(EntityManager em) {
        Goodreads goodreads = new Goodreads()
            .bookId(UPDATED_BOOK_ID)
            .gRBookId(UPDATED_G_R_BOOK_ID)
            .ratingCount(UPDATED_RATING_COUNT)
            .textReviewsCount(UPDATED_TEXT_REVIEWS_COUNT)
            .averageRating(UPDATED_AVERAGE_RATING)
            .lastUpdated(UPDATED_LAST_UPDATED);
        return goodreads;
    }

    @BeforeEach
    public void initTest() {
        goodreads = createEntity(em);
    }

    @Test
    @Transactional
    public void createGoodreads() throws Exception {
        int databaseSizeBeforeCreate = goodreadsRepository.findAll().size();

        // Create the Goodreads
        GoodreadsDTO goodreadsDTO = goodreadsMapper.toDto(goodreads);
        restGoodreadsMockMvc.perform(post("/api/goodreads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodreadsDTO)))
            .andExpect(status().isCreated());

        // Validate the Goodreads in the database
        List<Goodreads> goodreadsList = goodreadsRepository.findAll();
        assertThat(goodreadsList).hasSize(databaseSizeBeforeCreate + 1);
        Goodreads testGoodreads = goodreadsList.get(goodreadsList.size() - 1);
        assertThat(testGoodreads.getBookId()).isEqualTo(DEFAULT_BOOK_ID);
        assertThat(testGoodreads.getgRBookId()).isEqualTo(DEFAULT_G_R_BOOK_ID);
        assertThat(testGoodreads.getRatingCount()).isEqualTo(DEFAULT_RATING_COUNT);
        assertThat(testGoodreads.getTextReviewsCount()).isEqualTo(DEFAULT_TEXT_REVIEWS_COUNT);
        assertThat(testGoodreads.getAverageRating()).isEqualTo(DEFAULT_AVERAGE_RATING);
        assertThat(testGoodreads.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    public void createGoodreadsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = goodreadsRepository.findAll().size();

        // Create the Goodreads with an existing ID
        goodreads.setId(1L);
        GoodreadsDTO goodreadsDTO = goodreadsMapper.toDto(goodreads);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGoodreadsMockMvc.perform(post("/api/goodreads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodreadsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Goodreads in the database
        List<Goodreads> goodreadsList = goodreadsRepository.findAll();
        assertThat(goodreadsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGoodreads() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList
        restGoodreadsMockMvc.perform(get("/api/goodreads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goodreads.getId().intValue())))
            .andExpect(jsonPath("$.[*].bookId").value(hasItem(DEFAULT_BOOK_ID)))
            .andExpect(jsonPath("$.[*].gRBookId").value(hasItem(DEFAULT_G_R_BOOK_ID)))
            .andExpect(jsonPath("$.[*].ratingCount").value(hasItem(DEFAULT_RATING_COUNT)))
            .andExpect(jsonPath("$.[*].textReviewsCount").value(hasItem(DEFAULT_TEXT_REVIEWS_COUNT)))
            .andExpect(jsonPath("$.[*].averageRating").value(hasItem(DEFAULT_AVERAGE_RATING.doubleValue())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getGoodreads() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get the goodreads
        restGoodreadsMockMvc.perform(get("/api/goodreads/{id}", goodreads.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(goodreads.getId().intValue()))
            .andExpect(jsonPath("$.bookId").value(DEFAULT_BOOK_ID))
            .andExpect(jsonPath("$.gRBookId").value(DEFAULT_G_R_BOOK_ID))
            .andExpect(jsonPath("$.ratingCount").value(DEFAULT_RATING_COUNT))
            .andExpect(jsonPath("$.textReviewsCount").value(DEFAULT_TEXT_REVIEWS_COUNT))
            .andExpect(jsonPath("$.averageRating").value(DEFAULT_AVERAGE_RATING.doubleValue()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()));
    }

    @Test
    @Transactional
    public void getAllGoodreadsByBookIdIsEqualToSomething() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where bookId equals to DEFAULT_BOOK_ID
        defaultGoodreadsShouldBeFound("bookId.equals=" + DEFAULT_BOOK_ID);

        // Get all the goodreadsList where bookId equals to UPDATED_BOOK_ID
        defaultGoodreadsShouldNotBeFound("bookId.equals=" + UPDATED_BOOK_ID);
    }

    @Test
    @Transactional
    public void getAllGoodreadsByBookIdIsInShouldWork() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where bookId in DEFAULT_BOOK_ID or UPDATED_BOOK_ID
        defaultGoodreadsShouldBeFound("bookId.in=" + DEFAULT_BOOK_ID + "," + UPDATED_BOOK_ID);

        // Get all the goodreadsList where bookId equals to UPDATED_BOOK_ID
        defaultGoodreadsShouldNotBeFound("bookId.in=" + UPDATED_BOOK_ID);
    }

    @Test
    @Transactional
    public void getAllGoodreadsByBookIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where bookId is not null
        defaultGoodreadsShouldBeFound("bookId.specified=true");

        // Get all the goodreadsList where bookId is null
        defaultGoodreadsShouldNotBeFound("bookId.specified=false");
    }

    @Test
    @Transactional
    public void getAllGoodreadsByBookIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where bookId is greater than or equal to DEFAULT_BOOK_ID
        defaultGoodreadsShouldBeFound("bookId.greaterThanOrEqual=" + DEFAULT_BOOK_ID);

        // Get all the goodreadsList where bookId is greater than or equal to UPDATED_BOOK_ID
        defaultGoodreadsShouldNotBeFound("bookId.greaterThanOrEqual=" + UPDATED_BOOK_ID);
    }

    @Test
    @Transactional
    public void getAllGoodreadsByBookIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where bookId is less than or equal to DEFAULT_BOOK_ID
        defaultGoodreadsShouldBeFound("bookId.lessThanOrEqual=" + DEFAULT_BOOK_ID);

        // Get all the goodreadsList where bookId is less than or equal to SMALLER_BOOK_ID
        defaultGoodreadsShouldNotBeFound("bookId.lessThanOrEqual=" + SMALLER_BOOK_ID);
    }

    @Test
    @Transactional
    public void getAllGoodreadsByBookIdIsLessThanSomething() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where bookId is less than DEFAULT_BOOK_ID
        defaultGoodreadsShouldNotBeFound("bookId.lessThan=" + DEFAULT_BOOK_ID);

        // Get all the goodreadsList where bookId is less than UPDATED_BOOK_ID
        defaultGoodreadsShouldBeFound("bookId.lessThan=" + UPDATED_BOOK_ID);
    }

    @Test
    @Transactional
    public void getAllGoodreadsByBookIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where bookId is greater than DEFAULT_BOOK_ID
        defaultGoodreadsShouldNotBeFound("bookId.greaterThan=" + DEFAULT_BOOK_ID);

        // Get all the goodreadsList where bookId is greater than SMALLER_BOOK_ID
        defaultGoodreadsShouldBeFound("bookId.greaterThan=" + SMALLER_BOOK_ID);
    }


    @Test
    @Transactional
    public void getAllGoodreadsBygRBookIdIsEqualToSomething() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where gRBookId equals to DEFAULT_G_R_BOOK_ID
        defaultGoodreadsShouldBeFound("gRBookId.equals=" + DEFAULT_G_R_BOOK_ID);

        // Get all the goodreadsList where gRBookId equals to UPDATED_G_R_BOOK_ID
        defaultGoodreadsShouldNotBeFound("gRBookId.equals=" + UPDATED_G_R_BOOK_ID);
    }

    @Test
    @Transactional
    public void getAllGoodreadsBygRBookIdIsInShouldWork() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where gRBookId in DEFAULT_G_R_BOOK_ID or UPDATED_G_R_BOOK_ID
        defaultGoodreadsShouldBeFound("gRBookId.in=" + DEFAULT_G_R_BOOK_ID + "," + UPDATED_G_R_BOOK_ID);

        // Get all the goodreadsList where gRBookId equals to UPDATED_G_R_BOOK_ID
        defaultGoodreadsShouldNotBeFound("gRBookId.in=" + UPDATED_G_R_BOOK_ID);
    }

    @Test
    @Transactional
    public void getAllGoodreadsBygRBookIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where gRBookId is not null
        defaultGoodreadsShouldBeFound("gRBookId.specified=true");

        // Get all the goodreadsList where gRBookId is null
        defaultGoodreadsShouldNotBeFound("gRBookId.specified=false");
    }

    @Test
    @Transactional
    public void getAllGoodreadsBygRBookIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where gRBookId is greater than or equal to DEFAULT_G_R_BOOK_ID
        defaultGoodreadsShouldBeFound("gRBookId.greaterThanOrEqual=" + DEFAULT_G_R_BOOK_ID);

        // Get all the goodreadsList where gRBookId is greater than or equal to UPDATED_G_R_BOOK_ID
        defaultGoodreadsShouldNotBeFound("gRBookId.greaterThanOrEqual=" + UPDATED_G_R_BOOK_ID);
    }

    @Test
    @Transactional
    public void getAllGoodreadsBygRBookIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where gRBookId is less than or equal to DEFAULT_G_R_BOOK_ID
        defaultGoodreadsShouldBeFound("gRBookId.lessThanOrEqual=" + DEFAULT_G_R_BOOK_ID);

        // Get all the goodreadsList where gRBookId is less than or equal to SMALLER_G_R_BOOK_ID
        defaultGoodreadsShouldNotBeFound("gRBookId.lessThanOrEqual=" + SMALLER_G_R_BOOK_ID);
    }

    @Test
    @Transactional
    public void getAllGoodreadsBygRBookIdIsLessThanSomething() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where gRBookId is less than DEFAULT_G_R_BOOK_ID
        defaultGoodreadsShouldNotBeFound("gRBookId.lessThan=" + DEFAULT_G_R_BOOK_ID);

        // Get all the goodreadsList where gRBookId is less than UPDATED_G_R_BOOK_ID
        defaultGoodreadsShouldBeFound("gRBookId.lessThan=" + UPDATED_G_R_BOOK_ID);
    }

    @Test
    @Transactional
    public void getAllGoodreadsBygRBookIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where gRBookId is greater than DEFAULT_G_R_BOOK_ID
        defaultGoodreadsShouldNotBeFound("gRBookId.greaterThan=" + DEFAULT_G_R_BOOK_ID);

        // Get all the goodreadsList where gRBookId is greater than SMALLER_G_R_BOOK_ID
        defaultGoodreadsShouldBeFound("gRBookId.greaterThan=" + SMALLER_G_R_BOOK_ID);
    }


    @Test
    @Transactional
    public void getAllGoodreadsByRatingCountIsEqualToSomething() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where ratingCount equals to DEFAULT_RATING_COUNT
        defaultGoodreadsShouldBeFound("ratingCount.equals=" + DEFAULT_RATING_COUNT);

        // Get all the goodreadsList where ratingCount equals to UPDATED_RATING_COUNT
        defaultGoodreadsShouldNotBeFound("ratingCount.equals=" + UPDATED_RATING_COUNT);
    }

    @Test
    @Transactional
    public void getAllGoodreadsByRatingCountIsInShouldWork() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where ratingCount in DEFAULT_RATING_COUNT or UPDATED_RATING_COUNT
        defaultGoodreadsShouldBeFound("ratingCount.in=" + DEFAULT_RATING_COUNT + "," + UPDATED_RATING_COUNT);

        // Get all the goodreadsList where ratingCount equals to UPDATED_RATING_COUNT
        defaultGoodreadsShouldNotBeFound("ratingCount.in=" + UPDATED_RATING_COUNT);
    }

    @Test
    @Transactional
    public void getAllGoodreadsByRatingCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where ratingCount is not null
        defaultGoodreadsShouldBeFound("ratingCount.specified=true");

        // Get all the goodreadsList where ratingCount is null
        defaultGoodreadsShouldNotBeFound("ratingCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllGoodreadsByRatingCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where ratingCount is greater than or equal to DEFAULT_RATING_COUNT
        defaultGoodreadsShouldBeFound("ratingCount.greaterThanOrEqual=" + DEFAULT_RATING_COUNT);

        // Get all the goodreadsList where ratingCount is greater than or equal to UPDATED_RATING_COUNT
        defaultGoodreadsShouldNotBeFound("ratingCount.greaterThanOrEqual=" + UPDATED_RATING_COUNT);
    }

    @Test
    @Transactional
    public void getAllGoodreadsByRatingCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where ratingCount is less than or equal to DEFAULT_RATING_COUNT
        defaultGoodreadsShouldBeFound("ratingCount.lessThanOrEqual=" + DEFAULT_RATING_COUNT);

        // Get all the goodreadsList where ratingCount is less than or equal to SMALLER_RATING_COUNT
        defaultGoodreadsShouldNotBeFound("ratingCount.lessThanOrEqual=" + SMALLER_RATING_COUNT);
    }

    @Test
    @Transactional
    public void getAllGoodreadsByRatingCountIsLessThanSomething() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where ratingCount is less than DEFAULT_RATING_COUNT
        defaultGoodreadsShouldNotBeFound("ratingCount.lessThan=" + DEFAULT_RATING_COUNT);

        // Get all the goodreadsList where ratingCount is less than UPDATED_RATING_COUNT
        defaultGoodreadsShouldBeFound("ratingCount.lessThan=" + UPDATED_RATING_COUNT);
    }

    @Test
    @Transactional
    public void getAllGoodreadsByRatingCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where ratingCount is greater than DEFAULT_RATING_COUNT
        defaultGoodreadsShouldNotBeFound("ratingCount.greaterThan=" + DEFAULT_RATING_COUNT);

        // Get all the goodreadsList where ratingCount is greater than SMALLER_RATING_COUNT
        defaultGoodreadsShouldBeFound("ratingCount.greaterThan=" + SMALLER_RATING_COUNT);
    }


    @Test
    @Transactional
    public void getAllGoodreadsByTextReviewsCountIsEqualToSomething() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where textReviewsCount equals to DEFAULT_TEXT_REVIEWS_COUNT
        defaultGoodreadsShouldBeFound("textReviewsCount.equals=" + DEFAULT_TEXT_REVIEWS_COUNT);

        // Get all the goodreadsList where textReviewsCount equals to UPDATED_TEXT_REVIEWS_COUNT
        defaultGoodreadsShouldNotBeFound("textReviewsCount.equals=" + UPDATED_TEXT_REVIEWS_COUNT);
    }

    @Test
    @Transactional
    public void getAllGoodreadsByTextReviewsCountIsInShouldWork() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where textReviewsCount in DEFAULT_TEXT_REVIEWS_COUNT or UPDATED_TEXT_REVIEWS_COUNT
        defaultGoodreadsShouldBeFound("textReviewsCount.in=" + DEFAULT_TEXT_REVIEWS_COUNT + "," + UPDATED_TEXT_REVIEWS_COUNT);

        // Get all the goodreadsList where textReviewsCount equals to UPDATED_TEXT_REVIEWS_COUNT
        defaultGoodreadsShouldNotBeFound("textReviewsCount.in=" + UPDATED_TEXT_REVIEWS_COUNT);
    }

    @Test
    @Transactional
    public void getAllGoodreadsByTextReviewsCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where textReviewsCount is not null
        defaultGoodreadsShouldBeFound("textReviewsCount.specified=true");

        // Get all the goodreadsList where textReviewsCount is null
        defaultGoodreadsShouldNotBeFound("textReviewsCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllGoodreadsByTextReviewsCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where textReviewsCount is greater than or equal to DEFAULT_TEXT_REVIEWS_COUNT
        defaultGoodreadsShouldBeFound("textReviewsCount.greaterThanOrEqual=" + DEFAULT_TEXT_REVIEWS_COUNT);

        // Get all the goodreadsList where textReviewsCount is greater than or equal to UPDATED_TEXT_REVIEWS_COUNT
        defaultGoodreadsShouldNotBeFound("textReviewsCount.greaterThanOrEqual=" + UPDATED_TEXT_REVIEWS_COUNT);
    }

    @Test
    @Transactional
    public void getAllGoodreadsByTextReviewsCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where textReviewsCount is less than or equal to DEFAULT_TEXT_REVIEWS_COUNT
        defaultGoodreadsShouldBeFound("textReviewsCount.lessThanOrEqual=" + DEFAULT_TEXT_REVIEWS_COUNT);

        // Get all the goodreadsList where textReviewsCount is less than or equal to SMALLER_TEXT_REVIEWS_COUNT
        defaultGoodreadsShouldNotBeFound("textReviewsCount.lessThanOrEqual=" + SMALLER_TEXT_REVIEWS_COUNT);
    }

    @Test
    @Transactional
    public void getAllGoodreadsByTextReviewsCountIsLessThanSomething() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where textReviewsCount is less than DEFAULT_TEXT_REVIEWS_COUNT
        defaultGoodreadsShouldNotBeFound("textReviewsCount.lessThan=" + DEFAULT_TEXT_REVIEWS_COUNT);

        // Get all the goodreadsList where textReviewsCount is less than UPDATED_TEXT_REVIEWS_COUNT
        defaultGoodreadsShouldBeFound("textReviewsCount.lessThan=" + UPDATED_TEXT_REVIEWS_COUNT);
    }

    @Test
    @Transactional
    public void getAllGoodreadsByTextReviewsCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where textReviewsCount is greater than DEFAULT_TEXT_REVIEWS_COUNT
        defaultGoodreadsShouldNotBeFound("textReviewsCount.greaterThan=" + DEFAULT_TEXT_REVIEWS_COUNT);

        // Get all the goodreadsList where textReviewsCount is greater than SMALLER_TEXT_REVIEWS_COUNT
        defaultGoodreadsShouldBeFound("textReviewsCount.greaterThan=" + SMALLER_TEXT_REVIEWS_COUNT);
    }


    @Test
    @Transactional
    public void getAllGoodreadsByAverageRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where averageRating equals to DEFAULT_AVERAGE_RATING
        defaultGoodreadsShouldBeFound("averageRating.equals=" + DEFAULT_AVERAGE_RATING);

        // Get all the goodreadsList where averageRating equals to UPDATED_AVERAGE_RATING
        defaultGoodreadsShouldNotBeFound("averageRating.equals=" + UPDATED_AVERAGE_RATING);
    }

    @Test
    @Transactional
    public void getAllGoodreadsByAverageRatingIsInShouldWork() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where averageRating in DEFAULT_AVERAGE_RATING or UPDATED_AVERAGE_RATING
        defaultGoodreadsShouldBeFound("averageRating.in=" + DEFAULT_AVERAGE_RATING + "," + UPDATED_AVERAGE_RATING);

        // Get all the goodreadsList where averageRating equals to UPDATED_AVERAGE_RATING
        defaultGoodreadsShouldNotBeFound("averageRating.in=" + UPDATED_AVERAGE_RATING);
    }

    @Test
    @Transactional
    public void getAllGoodreadsByAverageRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where averageRating is not null
        defaultGoodreadsShouldBeFound("averageRating.specified=true");

        // Get all the goodreadsList where averageRating is null
        defaultGoodreadsShouldNotBeFound("averageRating.specified=false");
    }

    @Test
    @Transactional
    public void getAllGoodreadsByAverageRatingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where averageRating is greater than or equal to DEFAULT_AVERAGE_RATING
        defaultGoodreadsShouldBeFound("averageRating.greaterThanOrEqual=" + DEFAULT_AVERAGE_RATING);

        // Get all the goodreadsList where averageRating is greater than or equal to UPDATED_AVERAGE_RATING
        defaultGoodreadsShouldNotBeFound("averageRating.greaterThanOrEqual=" + UPDATED_AVERAGE_RATING);
    }

    @Test
    @Transactional
    public void getAllGoodreadsByAverageRatingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where averageRating is less than or equal to DEFAULT_AVERAGE_RATING
        defaultGoodreadsShouldBeFound("averageRating.lessThanOrEqual=" + DEFAULT_AVERAGE_RATING);

        // Get all the goodreadsList where averageRating is less than or equal to SMALLER_AVERAGE_RATING
        defaultGoodreadsShouldNotBeFound("averageRating.lessThanOrEqual=" + SMALLER_AVERAGE_RATING);
    }

    @Test
    @Transactional
    public void getAllGoodreadsByAverageRatingIsLessThanSomething() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where averageRating is less than DEFAULT_AVERAGE_RATING
        defaultGoodreadsShouldNotBeFound("averageRating.lessThan=" + DEFAULT_AVERAGE_RATING);

        // Get all the goodreadsList where averageRating is less than UPDATED_AVERAGE_RATING
        defaultGoodreadsShouldBeFound("averageRating.lessThan=" + UPDATED_AVERAGE_RATING);
    }

    @Test
    @Transactional
    public void getAllGoodreadsByAverageRatingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where averageRating is greater than DEFAULT_AVERAGE_RATING
        defaultGoodreadsShouldNotBeFound("averageRating.greaterThan=" + DEFAULT_AVERAGE_RATING);

        // Get all the goodreadsList where averageRating is greater than SMALLER_AVERAGE_RATING
        defaultGoodreadsShouldBeFound("averageRating.greaterThan=" + SMALLER_AVERAGE_RATING);
    }


    @Test
    @Transactional
    public void getAllGoodreadsByLastUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where lastUpdated equals to DEFAULT_LAST_UPDATED
        defaultGoodreadsShouldBeFound("lastUpdated.equals=" + DEFAULT_LAST_UPDATED);

        // Get all the goodreadsList where lastUpdated equals to UPDATED_LAST_UPDATED
        defaultGoodreadsShouldNotBeFound("lastUpdated.equals=" + UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    public void getAllGoodreadsByLastUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where lastUpdated in DEFAULT_LAST_UPDATED or UPDATED_LAST_UPDATED
        defaultGoodreadsShouldBeFound("lastUpdated.in=" + DEFAULT_LAST_UPDATED + "," + UPDATED_LAST_UPDATED);

        // Get all the goodreadsList where lastUpdated equals to UPDATED_LAST_UPDATED
        defaultGoodreadsShouldNotBeFound("lastUpdated.in=" + UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    public void getAllGoodreadsByLastUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where lastUpdated is not null
        defaultGoodreadsShouldBeFound("lastUpdated.specified=true");

        // Get all the goodreadsList where lastUpdated is null
        defaultGoodreadsShouldNotBeFound("lastUpdated.specified=false");
    }

    @Test
    @Transactional
    public void getAllGoodreadsByLastUpdatedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where lastUpdated is greater than or equal to DEFAULT_LAST_UPDATED
        defaultGoodreadsShouldBeFound("lastUpdated.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED);

        // Get all the goodreadsList where lastUpdated is greater than or equal to UPDATED_LAST_UPDATED
        defaultGoodreadsShouldNotBeFound("lastUpdated.greaterThanOrEqual=" + UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    public void getAllGoodreadsByLastUpdatedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where lastUpdated is less than or equal to DEFAULT_LAST_UPDATED
        defaultGoodreadsShouldBeFound("lastUpdated.lessThanOrEqual=" + DEFAULT_LAST_UPDATED);

        // Get all the goodreadsList where lastUpdated is less than or equal to SMALLER_LAST_UPDATED
        defaultGoodreadsShouldNotBeFound("lastUpdated.lessThanOrEqual=" + SMALLER_LAST_UPDATED);
    }

    @Test
    @Transactional
    public void getAllGoodreadsByLastUpdatedIsLessThanSomething() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where lastUpdated is less than DEFAULT_LAST_UPDATED
        defaultGoodreadsShouldNotBeFound("lastUpdated.lessThan=" + DEFAULT_LAST_UPDATED);

        // Get all the goodreadsList where lastUpdated is less than UPDATED_LAST_UPDATED
        defaultGoodreadsShouldBeFound("lastUpdated.lessThan=" + UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    public void getAllGoodreadsByLastUpdatedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        // Get all the goodreadsList where lastUpdated is greater than DEFAULT_LAST_UPDATED
        defaultGoodreadsShouldNotBeFound("lastUpdated.greaterThan=" + DEFAULT_LAST_UPDATED);

        // Get all the goodreadsList where lastUpdated is greater than SMALLER_LAST_UPDATED
        defaultGoodreadsShouldBeFound("lastUpdated.greaterThan=" + SMALLER_LAST_UPDATED);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGoodreadsShouldBeFound(String filter) throws Exception {
        restGoodreadsMockMvc.perform(get("/api/goodreads?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goodreads.getId().intValue())))
            .andExpect(jsonPath("$.[*].bookId").value(hasItem(DEFAULT_BOOK_ID)))
            .andExpect(jsonPath("$.[*].gRBookId").value(hasItem(DEFAULT_G_R_BOOK_ID)))
            .andExpect(jsonPath("$.[*].ratingCount").value(hasItem(DEFAULT_RATING_COUNT)))
            .andExpect(jsonPath("$.[*].textReviewsCount").value(hasItem(DEFAULT_TEXT_REVIEWS_COUNT)))
            .andExpect(jsonPath("$.[*].averageRating").value(hasItem(DEFAULT_AVERAGE_RATING.doubleValue())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())));

        // Check, that the count call also returns 1
        restGoodreadsMockMvc.perform(get("/api/goodreads/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGoodreadsShouldNotBeFound(String filter) throws Exception {
        restGoodreadsMockMvc.perform(get("/api/goodreads?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGoodreadsMockMvc.perform(get("/api/goodreads/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingGoodreads() throws Exception {
        // Get the goodreads
        restGoodreadsMockMvc.perform(get("/api/goodreads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGoodreads() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        int databaseSizeBeforeUpdate = goodreadsRepository.findAll().size();

        // Update the goodreads
        Goodreads updatedGoodreads = goodreadsRepository.findById(goodreads.getId()).get();
        // Disconnect from session so that the updates on updatedGoodreads are not directly saved in db
        em.detach(updatedGoodreads);
        updatedGoodreads
            .bookId(UPDATED_BOOK_ID)
            .gRBookId(UPDATED_G_R_BOOK_ID)
            .ratingCount(UPDATED_RATING_COUNT)
            .textReviewsCount(UPDATED_TEXT_REVIEWS_COUNT)
            .averageRating(UPDATED_AVERAGE_RATING)
            .lastUpdated(UPDATED_LAST_UPDATED);
        GoodreadsDTO goodreadsDTO = goodreadsMapper.toDto(updatedGoodreads);

        restGoodreadsMockMvc.perform(put("/api/goodreads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodreadsDTO)))
            .andExpect(status().isOk());

        // Validate the Goodreads in the database
        List<Goodreads> goodreadsList = goodreadsRepository.findAll();
        assertThat(goodreadsList).hasSize(databaseSizeBeforeUpdate);
        Goodreads testGoodreads = goodreadsList.get(goodreadsList.size() - 1);
        assertThat(testGoodreads.getBookId()).isEqualTo(UPDATED_BOOK_ID);
        assertThat(testGoodreads.getgRBookId()).isEqualTo(UPDATED_G_R_BOOK_ID);
        assertThat(testGoodreads.getRatingCount()).isEqualTo(UPDATED_RATING_COUNT);
        assertThat(testGoodreads.getTextReviewsCount()).isEqualTo(UPDATED_TEXT_REVIEWS_COUNT);
        assertThat(testGoodreads.getAverageRating()).isEqualTo(UPDATED_AVERAGE_RATING);
        assertThat(testGoodreads.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingGoodreads() throws Exception {
        int databaseSizeBeforeUpdate = goodreadsRepository.findAll().size();

        // Create the Goodreads
        GoodreadsDTO goodreadsDTO = goodreadsMapper.toDto(goodreads);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoodreadsMockMvc.perform(put("/api/goodreads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goodreadsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Goodreads in the database
        List<Goodreads> goodreadsList = goodreadsRepository.findAll();
        assertThat(goodreadsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGoodreads() throws Exception {
        // Initialize the database
        goodreadsRepository.saveAndFlush(goodreads);

        int databaseSizeBeforeDelete = goodreadsRepository.findAll().size();

        // Delete the goodreads
        restGoodreadsMockMvc.perform(delete("/api/goodreads/{id}", goodreads.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Goodreads> goodreadsList = goodreadsRepository.findAll();
        assertThat(goodreadsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Goodreads.class);
        Goodreads goodreads1 = new Goodreads();
        goodreads1.setId(1L);
        Goodreads goodreads2 = new Goodreads();
        goodreads2.setId(goodreads1.getId());
        assertThat(goodreads1).isEqualTo(goodreads2);
        goodreads2.setId(2L);
        assertThat(goodreads1).isNotEqualTo(goodreads2);
        goodreads1.setId(null);
        assertThat(goodreads1).isNotEqualTo(goodreads2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoodreadsDTO.class);
        GoodreadsDTO goodreadsDTO1 = new GoodreadsDTO();
        goodreadsDTO1.setId(1L);
        GoodreadsDTO goodreadsDTO2 = new GoodreadsDTO();
        assertThat(goodreadsDTO1).isNotEqualTo(goodreadsDTO2);
        goodreadsDTO2.setId(goodreadsDTO1.getId());
        assertThat(goodreadsDTO1).isEqualTo(goodreadsDTO2);
        goodreadsDTO2.setId(2L);
        assertThat(goodreadsDTO1).isNotEqualTo(goodreadsDTO2);
        goodreadsDTO1.setId(null);
        assertThat(goodreadsDTO1).isNotEqualTo(goodreadsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(goodreadsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(goodreadsMapper.fromId(null)).isNull();
    }
}
