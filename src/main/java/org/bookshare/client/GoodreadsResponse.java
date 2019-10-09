package org.bookshare.client;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "GoodreadsResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class GoodreadsResponse {

    @XmlElement(name = "search")
    private SearchElement search;

    public SearchElement getSearch() {
        return search;
    }

    public void setSearch(SearchElement search) {
        this.search = search;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class SearchElement {

        @XmlElement(name = "results")
        private ResultElement results;

        public ResultElement getResults() {
            return results;
        }

        public void setResults(ResultElement results) {
            this.results = results;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ResultElement {

        @XmlElement(name = "work")
        private WorkElement work;

        public WorkElement getWork() {
            return work;
        }

        public void setWork(WorkElement work) {
            this.work = work;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class WorkElement {

        @XmlElement(name = "id")
        private int id;

        @XmlElement(name = "ratings_count")
        private int ratingCount;

        @XmlElement(name = "text_reviews_count")
        private int textReviewsCount;

        @XmlElement(name = "original_publication_year")
        private int originalPublicationYear;

        @XmlElement(name = "original_publication_month")
        private int originalPublicationMonth;

        @XmlElement(name = "original_publication_day")
        private int originalPublicationDay;

        @XmlElement(name = "average_rating")
        private float averageRating;

        @XmlElement(name = "best_book")
        private BookElement book;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRatingCount() {
            return ratingCount;
        }

        public void setRatingCount(int ratingCount) {
            this.ratingCount = ratingCount;
        }

        public int getTextReviewsCount() {
            return textReviewsCount;
        }

        public void setTextReviewsCount(int textReviewsCount) {
            this.textReviewsCount = textReviewsCount;
        }

        public int getOriginalPublicationYear() {
            return originalPublicationYear;
        }

        public void setOriginalPublicationYear(int originalPublicationYear) {
            this.originalPublicationYear = originalPublicationYear;
        }

        public int getOriginalPublicationMonth() {
            return originalPublicationMonth;
        }

        public void setOriginalPublicationMonth(int originalPublicationMonth) {
            this.originalPublicationMonth = originalPublicationMonth;
        }

        public int getOriginalPublicationDay() {
            return originalPublicationDay;
        }

        public void setOriginalPublicationDay(int originalPublicationDay) {
            this.originalPublicationDay = originalPublicationDay;
        }

        public float getAverageRating() {
            return averageRating;
        }

        public void setAverageRating(float averageRating) {
            this.averageRating = averageRating;
        }

        public BookElement getBook() {
            return book;
        }

        public void setBook(BookElement book) {
            this.book = book;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class BookElement {

        @XmlElement(name = "id")
        private int id;

        @XmlElement(name = "title")
        private String title;

        @XmlElement(name = "image_url")
        private String imageUrl;

        @XmlElement(name = "author")
        private AuthorElement authorElement;

        @XmlAttribute(name = "type")
        private String type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public AuthorElement getAuthorElement() {
            return authorElement;
        }

        public void setAuthorElement(AuthorElement authorElement) {
            this.authorElement = authorElement;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class AuthorElement {

        @XmlElement(name = "id")
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
