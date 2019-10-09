package org.bookshare.job;

import io.github.jhipster.service.filter.StringFilter;
import org.bookshare.client.GoodreadClient;
import org.bookshare.client.GoodreadsResponse;
import org.bookshare.service.BookQueryService;
import org.bookshare.service.BookService;
import org.bookshare.service.GoodreadsService;
import org.bookshare.service.dto.BookCriteria;
import org.bookshare.service.dto.BookDTO;
import org.bookshare.service.dto.GoodreadsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Optional;

@Component
public class GoodreadDataJob {

    public static int FIRST_PAGE_INDEX = 1;

    public static int FIRST_ITEM_INDEX = 1;

    public static int ITEMS_PER_PAGE = 10;

    public static int MAX_ALLOWED_TOTAL_ITEMS = 20;

    @Autowired
    private BookQueryService bookQueryService;

    @Autowired
    private BookService bookService;

    @Autowired
    private GoodreadClient goodreadClient;

    @Autowired
    private GoodreadsService goodreadsService;

    //    @Scheduled(cron = "${application.jobs.goodreadCronExpr}")
    public void updateBookMetadata() {
        StringFilter languageFilter = new StringFilter();
        languageFilter.setEquals("English");

        StringFilter yearFilter = new StringFilter();
        yearFilter.setEquals("2019");

        BookCriteria bookCriteria = new BookCriteria();
        bookCriteria.setLanguage(languageFilter);
        bookCriteria.setYear(yearFilter);

        Page<BookDTO> books = bookQueryService.findByCriteria(bookCriteria,
            PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, "id")));
        for (BookDTO book : books.getContent()) {
            if (book.getIdentifier() != null && !book.getIdentifier().isEmpty()) {
                String isbn = book.getIdentifier().trim().replace(" ", "").replace("-", "");
                String[] isbns = isbn.split(",");
                String firstItem = isbns.length >= 1 ? isbns[0] : "";
                String secondItem = isbns.length >= 2 ? isbns[1] : "";
                if (firstItem.length() == 13) {
                    isbn = firstItem;
                } else if (secondItem.length() == 13) {
                    isbn = secondItem;
                } else if (firstItem.length() == 10) {
                    isbn = firstItem;
                } else if (secondItem.length() == 10) {
                    isbn = secondItem;
                } else {
                    isbn = "";
                }

                if (!isbn.isEmpty()) {
                    try {
                        GoodreadsResponse response = goodreadClient.getBookMetadataByISBN("HiBDoHWYziuz2zlNzIbLyw", isbn);
                        if (response.getSearch().getResults().getWork() != null &&
                            response.getSearch().getResults().getWork().getBook().getType().equals("Book")) {
                            String coverImageUrl = response.getSearch().getResults().getWork().getBook().getImageUrl();
                            if (coverImageUrl != null) {
                                coverImageUrl = coverImageUrl.replace("._SX98_", "");
                                book.setCoverurl(coverImageUrl);
                            }
                            book.setRatingCount(response.getSearch().getResults().getWork().getRatingCount());
                            book.setAverageRating(response.getSearch().getResults().getWork().getAverageRating());
                            book.setOriginalPublicationYear(response.getSearch().getResults().getWork().getOriginalPublicationYear());
                            book.setOriginalPublicationMonth(response.getSearch().getResults().getWork().getOriginalPublicationMonth());
                            book.setOriginalPublicationDay(response.getSearch().getResults().getWork().getOriginalPublicationDay());
                            bookService.save(book);

                            GoodreadsDTO goodreadsDTO = new GoodreadsDTO();
                            goodreadsDTO.setBookId(book.getId().intValue());
                            goodreadsDTO.setgRBookId(response.getSearch().getResults().getWork().getBook().getId());
                            goodreadsDTO.setRatingCount(response.getSearch().getResults().getWork().getRatingCount());
                            goodreadsDTO.setTextReviewsCount(response.getSearch().getResults().getWork().getTextReviewsCount());
                            goodreadsDTO.setAverageRating(response.getSearch().getResults().getWork().getAverageRating());
                            goodreadsDTO.setLastUpdated(LocalDate.now(ZoneId.systemDefault()));
                            goodreadsService.save(goodreadsDTO);
                        }
                    } catch (Exception ex) {

                    }
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {

                }
            }
        }

    }
}
