package org.bookshare.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link org.bookshare.domain.Goodreads} entity. This class is used
 * in {@link org.bookshare.web.rest.GoodreadsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /goodreads?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class GoodreadsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter bookId;

    private IntegerFilter gRBookId;

    private IntegerFilter ratingCount;

    private IntegerFilter textReviewsCount;

    private FloatFilter averageRating;

    private LocalDateFilter lastUpdated;

    public GoodreadsCriteria(){
    }

    public GoodreadsCriteria(GoodreadsCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.bookId = other.bookId == null ? null : other.bookId.copy();
        this.gRBookId = other.gRBookId == null ? null : other.gRBookId.copy();
        this.ratingCount = other.ratingCount == null ? null : other.ratingCount.copy();
        this.textReviewsCount = other.textReviewsCount == null ? null : other.textReviewsCount.copy();
        this.averageRating = other.averageRating == null ? null : other.averageRating.copy();
        this.lastUpdated = other.lastUpdated == null ? null : other.lastUpdated.copy();
    }

    @Override
    public GoodreadsCriteria copy() {
        return new GoodreadsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getBookId() {
        return bookId;
    }

    public void setBookId(IntegerFilter bookId) {
        this.bookId = bookId;
    }

    public IntegerFilter getgRBookId() {
        return gRBookId;
    }

    public void setgRBookId(IntegerFilter gRBookId) {
        this.gRBookId = gRBookId;
    }

    public IntegerFilter getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(IntegerFilter ratingCount) {
        this.ratingCount = ratingCount;
    }

    public IntegerFilter getTextReviewsCount() {
        return textReviewsCount;
    }

    public void setTextReviewsCount(IntegerFilter textReviewsCount) {
        this.textReviewsCount = textReviewsCount;
    }

    public FloatFilter getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(FloatFilter averageRating) {
        this.averageRating = averageRating;
    }

    public LocalDateFilter getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateFilter lastUpdated) {
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
        final GoodreadsCriteria that = (GoodreadsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(bookId, that.bookId) &&
            Objects.equals(gRBookId, that.gRBookId) &&
            Objects.equals(ratingCount, that.ratingCount) &&
            Objects.equals(textReviewsCount, that.textReviewsCount) &&
            Objects.equals(averageRating, that.averageRating) &&
            Objects.equals(lastUpdated, that.lastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        bookId,
        gRBookId,
        ratingCount,
        textReviewsCount,
        averageRating,
        lastUpdated
        );
    }

    @Override
    public String toString() {
        return "GoodreadsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (bookId != null ? "bookId=" + bookId + ", " : "") +
                (gRBookId != null ? "gRBookId=" + gRBookId + ", " : "") +
                (ratingCount != null ? "ratingCount=" + ratingCount + ", " : "") +
                (textReviewsCount != null ? "textReviewsCount=" + textReviewsCount + ", " : "") +
                (averageRating != null ? "averageRating=" + averageRating + ", " : "") +
                (lastUpdated != null ? "lastUpdated=" + lastUpdated + ", " : "") +
            "}";
    }

}
