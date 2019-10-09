package org.bookshare.web.rest;

import org.bookshare.BookshareApp;
import org.bookshare.domain.Book;
import org.bookshare.repository.BookRepository;
import org.bookshare.service.BookService;
import org.bookshare.service.dto.BookDTO;
import org.bookshare.service.mapper.BookMapper;
import org.bookshare.web.rest.errors.ExceptionTranslator;
import org.bookshare.service.dto.BookCriteria;
import org.bookshare.service.BookQueryService;

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
import java.util.List;

import static org.bookshare.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BookResource} REST controller.
 */
@SpringBootTest(classes = BookshareApp.class)
public class BookResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final String DEFAULT_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_PUBLISHER = "AAAAAAAAAA";
    private static final String UPDATED_PUBLISHER = "BBBBBBBBBB";

    private static final String DEFAULT_PAGES = "AAAAAAAAAA";
    private static final String UPDATED_PAGES = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final String DEFAULT_TOPIC = "AAAAAAAAAA";
    private static final String UPDATED_TOPIC = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_FILESIZE = "AAAAAAAAAA";
    private static final String UPDATED_FILESIZE = "BBBBBBBBBB";

    private static final String DEFAULT_EXTENSION = "AAAAAAAAAA";
    private static final String UPDATED_EXTENSION = "BBBBBBBBBB";

    private static final String DEFAULT_MD_5 = "AAAAAAAAAA";
    private static final String UPDATED_MD_5 = "BBBBBBBBBB";

    private static final String DEFAULT_COVERURL = "AAAAAAAAAA";
    private static final String UPDATED_COVERURL = "BBBBBBBBBB";

    private static final Float DEFAULT_AVERAGE_RATING = 1F;
    private static final Float UPDATED_AVERAGE_RATING = 2F;
    private static final Float SMALLER_AVERAGE_RATING = 1F - 1F;

    private static final Integer DEFAULT_RATING_COUNT = 1;
    private static final Integer UPDATED_RATING_COUNT = 2;
    private static final Integer SMALLER_RATING_COUNT = 1 - 1;

    private static final Integer DEFAULT_ORIGINAL_PUBLICATION_YEAR = 1;
    private static final Integer UPDATED_ORIGINAL_PUBLICATION_YEAR = 2;
    private static final Integer SMALLER_ORIGINAL_PUBLICATION_YEAR = 1 - 1;

    private static final Integer DEFAULT_ORIGINAL_PUBLICATION_MONTH = 1;
    private static final Integer UPDATED_ORIGINAL_PUBLICATION_MONTH = 2;
    private static final Integer SMALLER_ORIGINAL_PUBLICATION_MONTH = 1 - 1;

    private static final Integer DEFAULT_ORIGINAL_PUBLICATION_DAY = 1;
    private static final Integer UPDATED_ORIGINAL_PUBLICATION_DAY = 2;
    private static final Integer SMALLER_ORIGINAL_PUBLICATION_DAY = 1 - 1;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookQueryService bookQueryService;

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

    private MockMvc restBookMockMvc;

    private Book book;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BookResource bookResource = new BookResource(bookService, bookQueryService);
        this.restBookMockMvc = MockMvcBuilders.standaloneSetup(bookResource)
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
    public static Book createEntity(EntityManager em) {
        Book book = new Book()
            .title(DEFAULT_TITLE)
            .author(DEFAULT_AUTHOR)
            .year(DEFAULT_YEAR)
            .publisher(DEFAULT_PUBLISHER)
            .pages(DEFAULT_PAGES)
            .language(DEFAULT_LANGUAGE)
            .topic(DEFAULT_TOPIC)
            .identifier(DEFAULT_IDENTIFIER)
            .filesize(DEFAULT_FILESIZE)
            .extension(DEFAULT_EXTENSION)
            .md5(DEFAULT_MD_5)
            .coverurl(DEFAULT_COVERURL)
            .averageRating(DEFAULT_AVERAGE_RATING)
            .ratingCount(DEFAULT_RATING_COUNT)
            .originalPublicationYear(DEFAULT_ORIGINAL_PUBLICATION_YEAR)
            .originalPublicationMonth(DEFAULT_ORIGINAL_PUBLICATION_MONTH)
            .originalPublicationDay(DEFAULT_ORIGINAL_PUBLICATION_DAY);
        return book;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Book createUpdatedEntity(EntityManager em) {
        Book book = new Book()
            .title(UPDATED_TITLE)
            .author(UPDATED_AUTHOR)
            .year(UPDATED_YEAR)
            .publisher(UPDATED_PUBLISHER)
            .pages(UPDATED_PAGES)
            .language(UPDATED_LANGUAGE)
            .topic(UPDATED_TOPIC)
            .identifier(UPDATED_IDENTIFIER)
            .filesize(UPDATED_FILESIZE)
            .extension(UPDATED_EXTENSION)
            .md5(UPDATED_MD_5)
            .coverurl(UPDATED_COVERURL)
            .averageRating(UPDATED_AVERAGE_RATING)
            .ratingCount(UPDATED_RATING_COUNT)
            .originalPublicationYear(UPDATED_ORIGINAL_PUBLICATION_YEAR)
            .originalPublicationMonth(UPDATED_ORIGINAL_PUBLICATION_MONTH)
            .originalPublicationDay(UPDATED_ORIGINAL_PUBLICATION_DAY);
        return book;
    }

    @BeforeEach
    public void initTest() {
        book = createEntity(em);
    }

    @Test
    @Transactional
    public void createBook() throws Exception {
        int databaseSizeBeforeCreate = bookRepository.findAll().size();

        // Create the Book
        BookDTO bookDTO = bookMapper.toDto(book);
        restBookMockMvc.perform(post("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isCreated());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeCreate + 1);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBook.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testBook.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testBook.getPublisher()).isEqualTo(DEFAULT_PUBLISHER);
        assertThat(testBook.getPages()).isEqualTo(DEFAULT_PAGES);
        assertThat(testBook.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testBook.getTopic()).isEqualTo(DEFAULT_TOPIC);
        assertThat(testBook.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testBook.getFilesize()).isEqualTo(DEFAULT_FILESIZE);
        assertThat(testBook.getExtension()).isEqualTo(DEFAULT_EXTENSION);
        assertThat(testBook.getMd5()).isEqualTo(DEFAULT_MD_5);
        assertThat(testBook.getCoverurl()).isEqualTo(DEFAULT_COVERURL);
        assertThat(testBook.getAverageRating()).isEqualTo(DEFAULT_AVERAGE_RATING);
        assertThat(testBook.getRatingCount()).isEqualTo(DEFAULT_RATING_COUNT);
        assertThat(testBook.getOriginalPublicationYear()).isEqualTo(DEFAULT_ORIGINAL_PUBLICATION_YEAR);
        assertThat(testBook.getOriginalPublicationMonth()).isEqualTo(DEFAULT_ORIGINAL_PUBLICATION_MONTH);
        assertThat(testBook.getOriginalPublicationDay()).isEqualTo(DEFAULT_ORIGINAL_PUBLICATION_DAY);
    }

    @Test
    @Transactional
    public void createBookWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookRepository.findAll().size();

        // Create the Book with an existing ID
        book.setId(1L);
        BookDTO bookDTO = bookMapper.toDto(book);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookMockMvc.perform(post("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBooks() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList
        restBookMockMvc.perform(get("/api/books?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(book.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.toString())))
            .andExpect(jsonPath("$.[*].publisher").value(hasItem(DEFAULT_PUBLISHER.toString())))
            .andExpect(jsonPath("$.[*].pages").value(hasItem(DEFAULT_PAGES.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].topic").value(hasItem(DEFAULT_TOPIC.toString())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].filesize").value(hasItem(DEFAULT_FILESIZE.toString())))
            .andExpect(jsonPath("$.[*].extension").value(hasItem(DEFAULT_EXTENSION.toString())))
            .andExpect(jsonPath("$.[*].md5").value(hasItem(DEFAULT_MD_5.toString())))
            .andExpect(jsonPath("$.[*].coverurl").value(hasItem(DEFAULT_COVERURL.toString())))
            .andExpect(jsonPath("$.[*].averageRating").value(hasItem(DEFAULT_AVERAGE_RATING.doubleValue())))
            .andExpect(jsonPath("$.[*].ratingCount").value(hasItem(DEFAULT_RATING_COUNT)))
            .andExpect(jsonPath("$.[*].originalPublicationYear").value(hasItem(DEFAULT_ORIGINAL_PUBLICATION_YEAR)))
            .andExpect(jsonPath("$.[*].originalPublicationMonth").value(hasItem(DEFAULT_ORIGINAL_PUBLICATION_MONTH)))
            .andExpect(jsonPath("$.[*].originalPublicationDay").value(hasItem(DEFAULT_ORIGINAL_PUBLICATION_DAY)));
    }
    
    @Test
    @Transactional
    public void getBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get the book
        restBookMockMvc.perform(get("/api/books/{id}", book.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(book.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR.toString()))
            .andExpect(jsonPath("$.publisher").value(DEFAULT_PUBLISHER.toString()))
            .andExpect(jsonPath("$.pages").value(DEFAULT_PAGES.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()))
            .andExpect(jsonPath("$.topic").value(DEFAULT_TOPIC.toString()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.filesize").value(DEFAULT_FILESIZE.toString()))
            .andExpect(jsonPath("$.extension").value(DEFAULT_EXTENSION.toString()))
            .andExpect(jsonPath("$.md5").value(DEFAULT_MD_5.toString()))
            .andExpect(jsonPath("$.coverurl").value(DEFAULT_COVERURL.toString()))
            .andExpect(jsonPath("$.averageRating").value(DEFAULT_AVERAGE_RATING.doubleValue()))
            .andExpect(jsonPath("$.ratingCount").value(DEFAULT_RATING_COUNT))
            .andExpect(jsonPath("$.originalPublicationYear").value(DEFAULT_ORIGINAL_PUBLICATION_YEAR))
            .andExpect(jsonPath("$.originalPublicationMonth").value(DEFAULT_ORIGINAL_PUBLICATION_MONTH))
            .andExpect(jsonPath("$.originalPublicationDay").value(DEFAULT_ORIGINAL_PUBLICATION_DAY));
    }

    @Test
    @Transactional
    public void getAllBooksByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where title equals to DEFAULT_TITLE
        defaultBookShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the bookList where title equals to UPDATED_TITLE
        defaultBookShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllBooksByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultBookShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the bookList where title equals to UPDATED_TITLE
        defaultBookShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllBooksByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where title is not null
        defaultBookShouldBeFound("title.specified=true");

        // Get all the bookList where title is null
        defaultBookShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByAuthorIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where author equals to DEFAULT_AUTHOR
        defaultBookShouldBeFound("author.equals=" + DEFAULT_AUTHOR);

        // Get all the bookList where author equals to UPDATED_AUTHOR
        defaultBookShouldNotBeFound("author.equals=" + UPDATED_AUTHOR);
    }

    @Test
    @Transactional
    public void getAllBooksByAuthorIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where author in DEFAULT_AUTHOR or UPDATED_AUTHOR
        defaultBookShouldBeFound("author.in=" + DEFAULT_AUTHOR + "," + UPDATED_AUTHOR);

        // Get all the bookList where author equals to UPDATED_AUTHOR
        defaultBookShouldNotBeFound("author.in=" + UPDATED_AUTHOR);
    }

    @Test
    @Transactional
    public void getAllBooksByAuthorIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where author is not null
        defaultBookShouldBeFound("author.specified=true");

        // Get all the bookList where author is null
        defaultBookShouldNotBeFound("author.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where year equals to DEFAULT_YEAR
        defaultBookShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the bookList where year equals to UPDATED_YEAR
        defaultBookShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllBooksByYearIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultBookShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the bookList where year equals to UPDATED_YEAR
        defaultBookShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllBooksByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where year is not null
        defaultBookShouldBeFound("year.specified=true");

        // Get all the bookList where year is null
        defaultBookShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByPublisherIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publisher equals to DEFAULT_PUBLISHER
        defaultBookShouldBeFound("publisher.equals=" + DEFAULT_PUBLISHER);

        // Get all the bookList where publisher equals to UPDATED_PUBLISHER
        defaultBookShouldNotBeFound("publisher.equals=" + UPDATED_PUBLISHER);
    }

    @Test
    @Transactional
    public void getAllBooksByPublisherIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publisher in DEFAULT_PUBLISHER or UPDATED_PUBLISHER
        defaultBookShouldBeFound("publisher.in=" + DEFAULT_PUBLISHER + "," + UPDATED_PUBLISHER);

        // Get all the bookList where publisher equals to UPDATED_PUBLISHER
        defaultBookShouldNotBeFound("publisher.in=" + UPDATED_PUBLISHER);
    }

    @Test
    @Transactional
    public void getAllBooksByPublisherIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publisher is not null
        defaultBookShouldBeFound("publisher.specified=true");

        // Get all the bookList where publisher is null
        defaultBookShouldNotBeFound("publisher.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByPagesIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where pages equals to DEFAULT_PAGES
        defaultBookShouldBeFound("pages.equals=" + DEFAULT_PAGES);

        // Get all the bookList where pages equals to UPDATED_PAGES
        defaultBookShouldNotBeFound("pages.equals=" + UPDATED_PAGES);
    }

    @Test
    @Transactional
    public void getAllBooksByPagesIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where pages in DEFAULT_PAGES or UPDATED_PAGES
        defaultBookShouldBeFound("pages.in=" + DEFAULT_PAGES + "," + UPDATED_PAGES);

        // Get all the bookList where pages equals to UPDATED_PAGES
        defaultBookShouldNotBeFound("pages.in=" + UPDATED_PAGES);
    }

    @Test
    @Transactional
    public void getAllBooksByPagesIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where pages is not null
        defaultBookShouldBeFound("pages.specified=true");

        // Get all the bookList where pages is null
        defaultBookShouldNotBeFound("pages.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByLanguageIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where language equals to DEFAULT_LANGUAGE
        defaultBookShouldBeFound("language.equals=" + DEFAULT_LANGUAGE);

        // Get all the bookList where language equals to UPDATED_LANGUAGE
        defaultBookShouldNotBeFound("language.equals=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    public void getAllBooksByLanguageIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where language in DEFAULT_LANGUAGE or UPDATED_LANGUAGE
        defaultBookShouldBeFound("language.in=" + DEFAULT_LANGUAGE + "," + UPDATED_LANGUAGE);

        // Get all the bookList where language equals to UPDATED_LANGUAGE
        defaultBookShouldNotBeFound("language.in=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    public void getAllBooksByLanguageIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where language is not null
        defaultBookShouldBeFound("language.specified=true");

        // Get all the bookList where language is null
        defaultBookShouldNotBeFound("language.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByTopicIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where topic equals to DEFAULT_TOPIC
        defaultBookShouldBeFound("topic.equals=" + DEFAULT_TOPIC);

        // Get all the bookList where topic equals to UPDATED_TOPIC
        defaultBookShouldNotBeFound("topic.equals=" + UPDATED_TOPIC);
    }

    @Test
    @Transactional
    public void getAllBooksByTopicIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where topic in DEFAULT_TOPIC or UPDATED_TOPIC
        defaultBookShouldBeFound("topic.in=" + DEFAULT_TOPIC + "," + UPDATED_TOPIC);

        // Get all the bookList where topic equals to UPDATED_TOPIC
        defaultBookShouldNotBeFound("topic.in=" + UPDATED_TOPIC);
    }

    @Test
    @Transactional
    public void getAllBooksByTopicIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where topic is not null
        defaultBookShouldBeFound("topic.specified=true");

        // Get all the bookList where topic is null
        defaultBookShouldNotBeFound("topic.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByIdentifierIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where identifier equals to DEFAULT_IDENTIFIER
        defaultBookShouldBeFound("identifier.equals=" + DEFAULT_IDENTIFIER);

        // Get all the bookList where identifier equals to UPDATED_IDENTIFIER
        defaultBookShouldNotBeFound("identifier.equals=" + UPDATED_IDENTIFIER);
    }

    @Test
    @Transactional
    public void getAllBooksByIdentifierIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where identifier in DEFAULT_IDENTIFIER or UPDATED_IDENTIFIER
        defaultBookShouldBeFound("identifier.in=" + DEFAULT_IDENTIFIER + "," + UPDATED_IDENTIFIER);

        // Get all the bookList where identifier equals to UPDATED_IDENTIFIER
        defaultBookShouldNotBeFound("identifier.in=" + UPDATED_IDENTIFIER);
    }

    @Test
    @Transactional
    public void getAllBooksByIdentifierIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where identifier is not null
        defaultBookShouldBeFound("identifier.specified=true");

        // Get all the bookList where identifier is null
        defaultBookShouldNotBeFound("identifier.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByFilesizeIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where filesize equals to DEFAULT_FILESIZE
        defaultBookShouldBeFound("filesize.equals=" + DEFAULT_FILESIZE);

        // Get all the bookList where filesize equals to UPDATED_FILESIZE
        defaultBookShouldNotBeFound("filesize.equals=" + UPDATED_FILESIZE);
    }

    @Test
    @Transactional
    public void getAllBooksByFilesizeIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where filesize in DEFAULT_FILESIZE or UPDATED_FILESIZE
        defaultBookShouldBeFound("filesize.in=" + DEFAULT_FILESIZE + "," + UPDATED_FILESIZE);

        // Get all the bookList where filesize equals to UPDATED_FILESIZE
        defaultBookShouldNotBeFound("filesize.in=" + UPDATED_FILESIZE);
    }

    @Test
    @Transactional
    public void getAllBooksByFilesizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where filesize is not null
        defaultBookShouldBeFound("filesize.specified=true");

        // Get all the bookList where filesize is null
        defaultBookShouldNotBeFound("filesize.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByExtensionIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where extension equals to DEFAULT_EXTENSION
        defaultBookShouldBeFound("extension.equals=" + DEFAULT_EXTENSION);

        // Get all the bookList where extension equals to UPDATED_EXTENSION
        defaultBookShouldNotBeFound("extension.equals=" + UPDATED_EXTENSION);
    }

    @Test
    @Transactional
    public void getAllBooksByExtensionIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where extension in DEFAULT_EXTENSION or UPDATED_EXTENSION
        defaultBookShouldBeFound("extension.in=" + DEFAULT_EXTENSION + "," + UPDATED_EXTENSION);

        // Get all the bookList where extension equals to UPDATED_EXTENSION
        defaultBookShouldNotBeFound("extension.in=" + UPDATED_EXTENSION);
    }

    @Test
    @Transactional
    public void getAllBooksByExtensionIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where extension is not null
        defaultBookShouldBeFound("extension.specified=true");

        // Get all the bookList where extension is null
        defaultBookShouldNotBeFound("extension.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByMd5IsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where md5 equals to DEFAULT_MD_5
        defaultBookShouldBeFound("md5.equals=" + DEFAULT_MD_5);

        // Get all the bookList where md5 equals to UPDATED_MD_5
        defaultBookShouldNotBeFound("md5.equals=" + UPDATED_MD_5);
    }

    @Test
    @Transactional
    public void getAllBooksByMd5IsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where md5 in DEFAULT_MD_5 or UPDATED_MD_5
        defaultBookShouldBeFound("md5.in=" + DEFAULT_MD_5 + "," + UPDATED_MD_5);

        // Get all the bookList where md5 equals to UPDATED_MD_5
        defaultBookShouldNotBeFound("md5.in=" + UPDATED_MD_5);
    }

    @Test
    @Transactional
    public void getAllBooksByMd5IsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where md5 is not null
        defaultBookShouldBeFound("md5.specified=true");

        // Get all the bookList where md5 is null
        defaultBookShouldNotBeFound("md5.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByCoverurlIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where coverurl equals to DEFAULT_COVERURL
        defaultBookShouldBeFound("coverurl.equals=" + DEFAULT_COVERURL);

        // Get all the bookList where coverurl equals to UPDATED_COVERURL
        defaultBookShouldNotBeFound("coverurl.equals=" + UPDATED_COVERURL);
    }

    @Test
    @Transactional
    public void getAllBooksByCoverurlIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where coverurl in DEFAULT_COVERURL or UPDATED_COVERURL
        defaultBookShouldBeFound("coverurl.in=" + DEFAULT_COVERURL + "," + UPDATED_COVERURL);

        // Get all the bookList where coverurl equals to UPDATED_COVERURL
        defaultBookShouldNotBeFound("coverurl.in=" + UPDATED_COVERURL);
    }

    @Test
    @Transactional
    public void getAllBooksByCoverurlIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where coverurl is not null
        defaultBookShouldBeFound("coverurl.specified=true");

        // Get all the bookList where coverurl is null
        defaultBookShouldNotBeFound("coverurl.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByAverageRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where averageRating equals to DEFAULT_AVERAGE_RATING
        defaultBookShouldBeFound("averageRating.equals=" + DEFAULT_AVERAGE_RATING);

        // Get all the bookList where averageRating equals to UPDATED_AVERAGE_RATING
        defaultBookShouldNotBeFound("averageRating.equals=" + UPDATED_AVERAGE_RATING);
    }

    @Test
    @Transactional
    public void getAllBooksByAverageRatingIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where averageRating in DEFAULT_AVERAGE_RATING or UPDATED_AVERAGE_RATING
        defaultBookShouldBeFound("averageRating.in=" + DEFAULT_AVERAGE_RATING + "," + UPDATED_AVERAGE_RATING);

        // Get all the bookList where averageRating equals to UPDATED_AVERAGE_RATING
        defaultBookShouldNotBeFound("averageRating.in=" + UPDATED_AVERAGE_RATING);
    }

    @Test
    @Transactional
    public void getAllBooksByAverageRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where averageRating is not null
        defaultBookShouldBeFound("averageRating.specified=true");

        // Get all the bookList where averageRating is null
        defaultBookShouldNotBeFound("averageRating.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByAverageRatingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where averageRating is greater than or equal to DEFAULT_AVERAGE_RATING
        defaultBookShouldBeFound("averageRating.greaterThanOrEqual=" + DEFAULT_AVERAGE_RATING);

        // Get all the bookList where averageRating is greater than or equal to UPDATED_AVERAGE_RATING
        defaultBookShouldNotBeFound("averageRating.greaterThanOrEqual=" + UPDATED_AVERAGE_RATING);
    }

    @Test
    @Transactional
    public void getAllBooksByAverageRatingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where averageRating is less than or equal to DEFAULT_AVERAGE_RATING
        defaultBookShouldBeFound("averageRating.lessThanOrEqual=" + DEFAULT_AVERAGE_RATING);

        // Get all the bookList where averageRating is less than or equal to SMALLER_AVERAGE_RATING
        defaultBookShouldNotBeFound("averageRating.lessThanOrEqual=" + SMALLER_AVERAGE_RATING);
    }

    @Test
    @Transactional
    public void getAllBooksByAverageRatingIsLessThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where averageRating is less than DEFAULT_AVERAGE_RATING
        defaultBookShouldNotBeFound("averageRating.lessThan=" + DEFAULT_AVERAGE_RATING);

        // Get all the bookList where averageRating is less than UPDATED_AVERAGE_RATING
        defaultBookShouldBeFound("averageRating.lessThan=" + UPDATED_AVERAGE_RATING);
    }

    @Test
    @Transactional
    public void getAllBooksByAverageRatingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where averageRating is greater than DEFAULT_AVERAGE_RATING
        defaultBookShouldNotBeFound("averageRating.greaterThan=" + DEFAULT_AVERAGE_RATING);

        // Get all the bookList where averageRating is greater than SMALLER_AVERAGE_RATING
        defaultBookShouldBeFound("averageRating.greaterThan=" + SMALLER_AVERAGE_RATING);
    }


    @Test
    @Transactional
    public void getAllBooksByRatingCountIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where ratingCount equals to DEFAULT_RATING_COUNT
        defaultBookShouldBeFound("ratingCount.equals=" + DEFAULT_RATING_COUNT);

        // Get all the bookList where ratingCount equals to UPDATED_RATING_COUNT
        defaultBookShouldNotBeFound("ratingCount.equals=" + UPDATED_RATING_COUNT);
    }

    @Test
    @Transactional
    public void getAllBooksByRatingCountIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where ratingCount in DEFAULT_RATING_COUNT or UPDATED_RATING_COUNT
        defaultBookShouldBeFound("ratingCount.in=" + DEFAULT_RATING_COUNT + "," + UPDATED_RATING_COUNT);

        // Get all the bookList where ratingCount equals to UPDATED_RATING_COUNT
        defaultBookShouldNotBeFound("ratingCount.in=" + UPDATED_RATING_COUNT);
    }

    @Test
    @Transactional
    public void getAllBooksByRatingCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where ratingCount is not null
        defaultBookShouldBeFound("ratingCount.specified=true");

        // Get all the bookList where ratingCount is null
        defaultBookShouldNotBeFound("ratingCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByRatingCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where ratingCount is greater than or equal to DEFAULT_RATING_COUNT
        defaultBookShouldBeFound("ratingCount.greaterThanOrEqual=" + DEFAULT_RATING_COUNT);

        // Get all the bookList where ratingCount is greater than or equal to UPDATED_RATING_COUNT
        defaultBookShouldNotBeFound("ratingCount.greaterThanOrEqual=" + UPDATED_RATING_COUNT);
    }

    @Test
    @Transactional
    public void getAllBooksByRatingCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where ratingCount is less than or equal to DEFAULT_RATING_COUNT
        defaultBookShouldBeFound("ratingCount.lessThanOrEqual=" + DEFAULT_RATING_COUNT);

        // Get all the bookList where ratingCount is less than or equal to SMALLER_RATING_COUNT
        defaultBookShouldNotBeFound("ratingCount.lessThanOrEqual=" + SMALLER_RATING_COUNT);
    }

    @Test
    @Transactional
    public void getAllBooksByRatingCountIsLessThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where ratingCount is less than DEFAULT_RATING_COUNT
        defaultBookShouldNotBeFound("ratingCount.lessThan=" + DEFAULT_RATING_COUNT);

        // Get all the bookList where ratingCount is less than UPDATED_RATING_COUNT
        defaultBookShouldBeFound("ratingCount.lessThan=" + UPDATED_RATING_COUNT);
    }

    @Test
    @Transactional
    public void getAllBooksByRatingCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where ratingCount is greater than DEFAULT_RATING_COUNT
        defaultBookShouldNotBeFound("ratingCount.greaterThan=" + DEFAULT_RATING_COUNT);

        // Get all the bookList where ratingCount is greater than SMALLER_RATING_COUNT
        defaultBookShouldBeFound("ratingCount.greaterThan=" + SMALLER_RATING_COUNT);
    }


    @Test
    @Transactional
    public void getAllBooksByOriginalPublicationYearIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where originalPublicationYear equals to DEFAULT_ORIGINAL_PUBLICATION_YEAR
        defaultBookShouldBeFound("originalPublicationYear.equals=" + DEFAULT_ORIGINAL_PUBLICATION_YEAR);

        // Get all the bookList where originalPublicationYear equals to UPDATED_ORIGINAL_PUBLICATION_YEAR
        defaultBookShouldNotBeFound("originalPublicationYear.equals=" + UPDATED_ORIGINAL_PUBLICATION_YEAR);
    }

    @Test
    @Transactional
    public void getAllBooksByOriginalPublicationYearIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where originalPublicationYear in DEFAULT_ORIGINAL_PUBLICATION_YEAR or UPDATED_ORIGINAL_PUBLICATION_YEAR
        defaultBookShouldBeFound("originalPublicationYear.in=" + DEFAULT_ORIGINAL_PUBLICATION_YEAR + "," + UPDATED_ORIGINAL_PUBLICATION_YEAR);

        // Get all the bookList where originalPublicationYear equals to UPDATED_ORIGINAL_PUBLICATION_YEAR
        defaultBookShouldNotBeFound("originalPublicationYear.in=" + UPDATED_ORIGINAL_PUBLICATION_YEAR);
    }

    @Test
    @Transactional
    public void getAllBooksByOriginalPublicationYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where originalPublicationYear is not null
        defaultBookShouldBeFound("originalPublicationYear.specified=true");

        // Get all the bookList where originalPublicationYear is null
        defaultBookShouldNotBeFound("originalPublicationYear.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByOriginalPublicationYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where originalPublicationYear is greater than or equal to DEFAULT_ORIGINAL_PUBLICATION_YEAR
        defaultBookShouldBeFound("originalPublicationYear.greaterThanOrEqual=" + DEFAULT_ORIGINAL_PUBLICATION_YEAR);

        // Get all the bookList where originalPublicationYear is greater than or equal to UPDATED_ORIGINAL_PUBLICATION_YEAR
        defaultBookShouldNotBeFound("originalPublicationYear.greaterThanOrEqual=" + UPDATED_ORIGINAL_PUBLICATION_YEAR);
    }

    @Test
    @Transactional
    public void getAllBooksByOriginalPublicationYearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where originalPublicationYear is less than or equal to DEFAULT_ORIGINAL_PUBLICATION_YEAR
        defaultBookShouldBeFound("originalPublicationYear.lessThanOrEqual=" + DEFAULT_ORIGINAL_PUBLICATION_YEAR);

        // Get all the bookList where originalPublicationYear is less than or equal to SMALLER_ORIGINAL_PUBLICATION_YEAR
        defaultBookShouldNotBeFound("originalPublicationYear.lessThanOrEqual=" + SMALLER_ORIGINAL_PUBLICATION_YEAR);
    }

    @Test
    @Transactional
    public void getAllBooksByOriginalPublicationYearIsLessThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where originalPublicationYear is less than DEFAULT_ORIGINAL_PUBLICATION_YEAR
        defaultBookShouldNotBeFound("originalPublicationYear.lessThan=" + DEFAULT_ORIGINAL_PUBLICATION_YEAR);

        // Get all the bookList where originalPublicationYear is less than UPDATED_ORIGINAL_PUBLICATION_YEAR
        defaultBookShouldBeFound("originalPublicationYear.lessThan=" + UPDATED_ORIGINAL_PUBLICATION_YEAR);
    }

    @Test
    @Transactional
    public void getAllBooksByOriginalPublicationYearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where originalPublicationYear is greater than DEFAULT_ORIGINAL_PUBLICATION_YEAR
        defaultBookShouldNotBeFound("originalPublicationYear.greaterThan=" + DEFAULT_ORIGINAL_PUBLICATION_YEAR);

        // Get all the bookList where originalPublicationYear is greater than SMALLER_ORIGINAL_PUBLICATION_YEAR
        defaultBookShouldBeFound("originalPublicationYear.greaterThan=" + SMALLER_ORIGINAL_PUBLICATION_YEAR);
    }


    @Test
    @Transactional
    public void getAllBooksByOriginalPublicationMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where originalPublicationMonth equals to DEFAULT_ORIGINAL_PUBLICATION_MONTH
        defaultBookShouldBeFound("originalPublicationMonth.equals=" + DEFAULT_ORIGINAL_PUBLICATION_MONTH);

        // Get all the bookList where originalPublicationMonth equals to UPDATED_ORIGINAL_PUBLICATION_MONTH
        defaultBookShouldNotBeFound("originalPublicationMonth.equals=" + UPDATED_ORIGINAL_PUBLICATION_MONTH);
    }

    @Test
    @Transactional
    public void getAllBooksByOriginalPublicationMonthIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where originalPublicationMonth in DEFAULT_ORIGINAL_PUBLICATION_MONTH or UPDATED_ORIGINAL_PUBLICATION_MONTH
        defaultBookShouldBeFound("originalPublicationMonth.in=" + DEFAULT_ORIGINAL_PUBLICATION_MONTH + "," + UPDATED_ORIGINAL_PUBLICATION_MONTH);

        // Get all the bookList where originalPublicationMonth equals to UPDATED_ORIGINAL_PUBLICATION_MONTH
        defaultBookShouldNotBeFound("originalPublicationMonth.in=" + UPDATED_ORIGINAL_PUBLICATION_MONTH);
    }

    @Test
    @Transactional
    public void getAllBooksByOriginalPublicationMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where originalPublicationMonth is not null
        defaultBookShouldBeFound("originalPublicationMonth.specified=true");

        // Get all the bookList where originalPublicationMonth is null
        defaultBookShouldNotBeFound("originalPublicationMonth.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByOriginalPublicationMonthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where originalPublicationMonth is greater than or equal to DEFAULT_ORIGINAL_PUBLICATION_MONTH
        defaultBookShouldBeFound("originalPublicationMonth.greaterThanOrEqual=" + DEFAULT_ORIGINAL_PUBLICATION_MONTH);

        // Get all the bookList where originalPublicationMonth is greater than or equal to UPDATED_ORIGINAL_PUBLICATION_MONTH
        defaultBookShouldNotBeFound("originalPublicationMonth.greaterThanOrEqual=" + UPDATED_ORIGINAL_PUBLICATION_MONTH);
    }

    @Test
    @Transactional
    public void getAllBooksByOriginalPublicationMonthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where originalPublicationMonth is less than or equal to DEFAULT_ORIGINAL_PUBLICATION_MONTH
        defaultBookShouldBeFound("originalPublicationMonth.lessThanOrEqual=" + DEFAULT_ORIGINAL_PUBLICATION_MONTH);

        // Get all the bookList where originalPublicationMonth is less than or equal to SMALLER_ORIGINAL_PUBLICATION_MONTH
        defaultBookShouldNotBeFound("originalPublicationMonth.lessThanOrEqual=" + SMALLER_ORIGINAL_PUBLICATION_MONTH);
    }

    @Test
    @Transactional
    public void getAllBooksByOriginalPublicationMonthIsLessThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where originalPublicationMonth is less than DEFAULT_ORIGINAL_PUBLICATION_MONTH
        defaultBookShouldNotBeFound("originalPublicationMonth.lessThan=" + DEFAULT_ORIGINAL_PUBLICATION_MONTH);

        // Get all the bookList where originalPublicationMonth is less than UPDATED_ORIGINAL_PUBLICATION_MONTH
        defaultBookShouldBeFound("originalPublicationMonth.lessThan=" + UPDATED_ORIGINAL_PUBLICATION_MONTH);
    }

    @Test
    @Transactional
    public void getAllBooksByOriginalPublicationMonthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where originalPublicationMonth is greater than DEFAULT_ORIGINAL_PUBLICATION_MONTH
        defaultBookShouldNotBeFound("originalPublicationMonth.greaterThan=" + DEFAULT_ORIGINAL_PUBLICATION_MONTH);

        // Get all the bookList where originalPublicationMonth is greater than SMALLER_ORIGINAL_PUBLICATION_MONTH
        defaultBookShouldBeFound("originalPublicationMonth.greaterThan=" + SMALLER_ORIGINAL_PUBLICATION_MONTH);
    }


    @Test
    @Transactional
    public void getAllBooksByOriginalPublicationDayIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where originalPublicationDay equals to DEFAULT_ORIGINAL_PUBLICATION_DAY
        defaultBookShouldBeFound("originalPublicationDay.equals=" + DEFAULT_ORIGINAL_PUBLICATION_DAY);

        // Get all the bookList where originalPublicationDay equals to UPDATED_ORIGINAL_PUBLICATION_DAY
        defaultBookShouldNotBeFound("originalPublicationDay.equals=" + UPDATED_ORIGINAL_PUBLICATION_DAY);
    }

    @Test
    @Transactional
    public void getAllBooksByOriginalPublicationDayIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where originalPublicationDay in DEFAULT_ORIGINAL_PUBLICATION_DAY or UPDATED_ORIGINAL_PUBLICATION_DAY
        defaultBookShouldBeFound("originalPublicationDay.in=" + DEFAULT_ORIGINAL_PUBLICATION_DAY + "," + UPDATED_ORIGINAL_PUBLICATION_DAY);

        // Get all the bookList where originalPublicationDay equals to UPDATED_ORIGINAL_PUBLICATION_DAY
        defaultBookShouldNotBeFound("originalPublicationDay.in=" + UPDATED_ORIGINAL_PUBLICATION_DAY);
    }

    @Test
    @Transactional
    public void getAllBooksByOriginalPublicationDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where originalPublicationDay is not null
        defaultBookShouldBeFound("originalPublicationDay.specified=true");

        // Get all the bookList where originalPublicationDay is null
        defaultBookShouldNotBeFound("originalPublicationDay.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByOriginalPublicationDayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where originalPublicationDay is greater than or equal to DEFAULT_ORIGINAL_PUBLICATION_DAY
        defaultBookShouldBeFound("originalPublicationDay.greaterThanOrEqual=" + DEFAULT_ORIGINAL_PUBLICATION_DAY);

        // Get all the bookList where originalPublicationDay is greater than or equal to UPDATED_ORIGINAL_PUBLICATION_DAY
        defaultBookShouldNotBeFound("originalPublicationDay.greaterThanOrEqual=" + UPDATED_ORIGINAL_PUBLICATION_DAY);
    }

    @Test
    @Transactional
    public void getAllBooksByOriginalPublicationDayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where originalPublicationDay is less than or equal to DEFAULT_ORIGINAL_PUBLICATION_DAY
        defaultBookShouldBeFound("originalPublicationDay.lessThanOrEqual=" + DEFAULT_ORIGINAL_PUBLICATION_DAY);

        // Get all the bookList where originalPublicationDay is less than or equal to SMALLER_ORIGINAL_PUBLICATION_DAY
        defaultBookShouldNotBeFound("originalPublicationDay.lessThanOrEqual=" + SMALLER_ORIGINAL_PUBLICATION_DAY);
    }

    @Test
    @Transactional
    public void getAllBooksByOriginalPublicationDayIsLessThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where originalPublicationDay is less than DEFAULT_ORIGINAL_PUBLICATION_DAY
        defaultBookShouldNotBeFound("originalPublicationDay.lessThan=" + DEFAULT_ORIGINAL_PUBLICATION_DAY);

        // Get all the bookList where originalPublicationDay is less than UPDATED_ORIGINAL_PUBLICATION_DAY
        defaultBookShouldBeFound("originalPublicationDay.lessThan=" + UPDATED_ORIGINAL_PUBLICATION_DAY);
    }

    @Test
    @Transactional
    public void getAllBooksByOriginalPublicationDayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where originalPublicationDay is greater than DEFAULT_ORIGINAL_PUBLICATION_DAY
        defaultBookShouldNotBeFound("originalPublicationDay.greaterThan=" + DEFAULT_ORIGINAL_PUBLICATION_DAY);

        // Get all the bookList where originalPublicationDay is greater than SMALLER_ORIGINAL_PUBLICATION_DAY
        defaultBookShouldBeFound("originalPublicationDay.greaterThan=" + SMALLER_ORIGINAL_PUBLICATION_DAY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBookShouldBeFound(String filter) throws Exception {
        restBookMockMvc.perform(get("/api/books?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(book.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].publisher").value(hasItem(DEFAULT_PUBLISHER)))
            .andExpect(jsonPath("$.[*].pages").value(hasItem(DEFAULT_PAGES)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)))
            .andExpect(jsonPath("$.[*].topic").value(hasItem(DEFAULT_TOPIC)))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER)))
            .andExpect(jsonPath("$.[*].filesize").value(hasItem(DEFAULT_FILESIZE)))
            .andExpect(jsonPath("$.[*].extension").value(hasItem(DEFAULT_EXTENSION)))
            .andExpect(jsonPath("$.[*].md5").value(hasItem(DEFAULT_MD_5)))
            .andExpect(jsonPath("$.[*].coverurl").value(hasItem(DEFAULT_COVERURL)))
            .andExpect(jsonPath("$.[*].averageRating").value(hasItem(DEFAULT_AVERAGE_RATING.doubleValue())))
            .andExpect(jsonPath("$.[*].ratingCount").value(hasItem(DEFAULT_RATING_COUNT)))
            .andExpect(jsonPath("$.[*].originalPublicationYear").value(hasItem(DEFAULT_ORIGINAL_PUBLICATION_YEAR)))
            .andExpect(jsonPath("$.[*].originalPublicationMonth").value(hasItem(DEFAULT_ORIGINAL_PUBLICATION_MONTH)))
            .andExpect(jsonPath("$.[*].originalPublicationDay").value(hasItem(DEFAULT_ORIGINAL_PUBLICATION_DAY)));

        // Check, that the count call also returns 1
        restBookMockMvc.perform(get("/api/books/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBookShouldNotBeFound(String filter) throws Exception {
        restBookMockMvc.perform(get("/api/books?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBookMockMvc.perform(get("/api/books/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBook() throws Exception {
        // Get the book
        restBookMockMvc.perform(get("/api/books/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Update the book
        Book updatedBook = bookRepository.findById(book.getId()).get();
        // Disconnect from session so that the updates on updatedBook are not directly saved in db
        em.detach(updatedBook);
        updatedBook
            .title(UPDATED_TITLE)
            .author(UPDATED_AUTHOR)
            .year(UPDATED_YEAR)
            .publisher(UPDATED_PUBLISHER)
            .pages(UPDATED_PAGES)
            .language(UPDATED_LANGUAGE)
            .topic(UPDATED_TOPIC)
            .identifier(UPDATED_IDENTIFIER)
            .filesize(UPDATED_FILESIZE)
            .extension(UPDATED_EXTENSION)
            .md5(UPDATED_MD_5)
            .coverurl(UPDATED_COVERURL)
            .averageRating(UPDATED_AVERAGE_RATING)
            .ratingCount(UPDATED_RATING_COUNT)
            .originalPublicationYear(UPDATED_ORIGINAL_PUBLICATION_YEAR)
            .originalPublicationMonth(UPDATED_ORIGINAL_PUBLICATION_MONTH)
            .originalPublicationDay(UPDATED_ORIGINAL_PUBLICATION_DAY);
        BookDTO bookDTO = bookMapper.toDto(updatedBook);

        restBookMockMvc.perform(put("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isOk());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBook.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testBook.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testBook.getPublisher()).isEqualTo(UPDATED_PUBLISHER);
        assertThat(testBook.getPages()).isEqualTo(UPDATED_PAGES);
        assertThat(testBook.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testBook.getTopic()).isEqualTo(UPDATED_TOPIC);
        assertThat(testBook.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testBook.getFilesize()).isEqualTo(UPDATED_FILESIZE);
        assertThat(testBook.getExtension()).isEqualTo(UPDATED_EXTENSION);
        assertThat(testBook.getMd5()).isEqualTo(UPDATED_MD_5);
        assertThat(testBook.getCoverurl()).isEqualTo(UPDATED_COVERURL);
        assertThat(testBook.getAverageRating()).isEqualTo(UPDATED_AVERAGE_RATING);
        assertThat(testBook.getRatingCount()).isEqualTo(UPDATED_RATING_COUNT);
        assertThat(testBook.getOriginalPublicationYear()).isEqualTo(UPDATED_ORIGINAL_PUBLICATION_YEAR);
        assertThat(testBook.getOriginalPublicationMonth()).isEqualTo(UPDATED_ORIGINAL_PUBLICATION_MONTH);
        assertThat(testBook.getOriginalPublicationDay()).isEqualTo(UPDATED_ORIGINAL_PUBLICATION_DAY);
    }

    @Test
    @Transactional
    public void updateNonExistingBook() throws Exception {
        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Create the Book
        BookDTO bookDTO = bookMapper.toDto(book);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookMockMvc.perform(put("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        int databaseSizeBeforeDelete = bookRepository.findAll().size();

        // Delete the book
        restBookMockMvc.perform(delete("/api/books/{id}", book.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Book.class);
        Book book1 = new Book();
        book1.setId(1L);
        Book book2 = new Book();
        book2.setId(book1.getId());
        assertThat(book1).isEqualTo(book2);
        book2.setId(2L);
        assertThat(book1).isNotEqualTo(book2);
        book1.setId(null);
        assertThat(book1).isNotEqualTo(book2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookDTO.class);
        BookDTO bookDTO1 = new BookDTO();
        bookDTO1.setId(1L);
        BookDTO bookDTO2 = new BookDTO();
        assertThat(bookDTO1).isNotEqualTo(bookDTO2);
        bookDTO2.setId(bookDTO1.getId());
        assertThat(bookDTO1).isEqualTo(bookDTO2);
        bookDTO2.setId(2L);
        assertThat(bookDTO1).isNotEqualTo(bookDTO2);
        bookDTO1.setId(null);
        assertThat(bookDTO1).isNotEqualTo(bookDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bookMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bookMapper.fromId(null)).isNull();
    }
}
