package org.bookshare.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.bookshare.domain.Goodreads} entity.
 */
public class GoodreadsDTO implements Serializable {

    private Long id;

    private Integer bookId;

    private Integer gRBookId;

    private Integer ratingCount;

    private Integer textReviewsCount;

    private Float averageRating;

    private LocalDate lastUpdated;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getgRBookId() {
        return gRBookId;
    }

    public void setgRBookId(Integer gRBookId) {
        this.gRBookId = gRBookId;
    }

    public Integer getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
    }

    public Integer getTextReviewsCount() {
        return textReviewsCount;
    }

    public void setTextReviewsCount(Integer textReviewsCount) {
        this.textReviewsCount = textReviewsCount;
    }

    public Float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Float averageRating) {
        this.averageRating = averageRating;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GoodreadsDTO goodreadsDTO = (GoodreadsDTO) o;
        if (goodreadsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), goodreadsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GoodreadsDTO{" +
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
