package org.bookshare.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Goodreads.
 */
@Entity
@Table(name = "goodreads")
public class Goodreads implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "gr_book_id")
    private Integer gRBookId;

    @Column(name = "rating_count")
    private Integer ratingCount;

    @Column(name = "text_reviews_count")
    private Integer textReviewsCount;

    @Column(name = "average_rating")
    private Float averageRating;

    @Column(name = "last_updated")
    private LocalDate lastUpdated;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBookId() {
        return bookId;
    }

    public Goodreads bookId(Integer bookId) {
        this.bookId = bookId;
        return this;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getgRBookId() {
        return gRBookId;
    }

    public Goodreads gRBookId(Integer gRBookId) {
        this.gRBookId = gRBookId;
        return this;
    }

    public void setgRBookId(Integer gRBookId) {
        this.gRBookId = gRBookId;
    }

    public Integer getRatingCount() {
        return ratingCount;
    }

    public Goodreads ratingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
        return this;
    }

    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
    }

    public Integer getTextReviewsCount() {
        return textReviewsCount;
    }

    public Goodreads textReviewsCount(Integer textReviewsCount) {
        this.textReviewsCount = textReviewsCount;
        return this;
    }

    public void setTextReviewsCount(Integer textReviewsCount) {
        this.textReviewsCount = textReviewsCount;
    }

    public Float getAverageRating() {
        return averageRating;
    }

    public Goodreads averageRating(Float averageRating) {
        this.averageRating = averageRating;
        return this;
    }

    public void setAverageRating(Float averageRating) {
        this.averageRating = averageRating;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public Goodreads lastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Goodreads)) {
            return false;
        }
        return id != null && id.equals(((Goodreads) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Goodreads{" +
            "id=" + getId() +
            ", bookId=" + getBookId() +
            ", gRBookId=" + getgRBookId() +
            ", ratingCount=" + getRatingCount() +
            ", textReviewsCount=" + getTextReviewsCount() +
            ", averageRating=" + getAverageRating() +
            ", lastUpdated='" + getLastUpdated() + "'" +
            "}";
    }
}
