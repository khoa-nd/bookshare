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

/**
 * Criteria class for the {@link org.bookshare.domain.Book} entity. This class is used
 * in {@link org.bookshare.web.rest.BookResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /books?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BookCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter author;

    private StringFilter year;

    private StringFilter publisher;

    private StringFilter pages;

    private StringFilter language;

    private StringFilter topic;

    private StringFilter identifier;

    private StringFilter filesize;

    private StringFilter extension;

    private StringFilter md5;

    private StringFilter coverurl;

    private FloatFilter averageRating;

    private IntegerFilter ratingCount;

    private IntegerFilter originalPublicationYear;

    private IntegerFilter originalPublicationMonth;

    private IntegerFilter originalPublicationDay;

    public BookCriteria(){
    }

    public BookCriteria(BookCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.author = other.author == null ? null : other.author.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.publisher = other.publisher == null ? null : other.publisher.copy();
        this.pages = other.pages == null ? null : other.pages.copy();
        this.language = other.language == null ? null : other.language.copy();
        this.topic = other.topic == null ? null : other.topic.copy();
        this.identifier = other.identifier == null ? null : other.identifier.copy();
        this.filesize = other.filesize == null ? null : other.filesize.copy();
        this.extension = other.extension == null ? null : other.extension.copy();
        this.md5 = other.md5 == null ? null : other.md5.copy();
        this.coverurl = other.coverurl == null ? null : other.coverurl.copy();
        this.averageRating = other.averageRating == null ? null : other.averageRating.copy();
        this.ratingCount = other.ratingCount == null ? null : other.ratingCount.copy();
        this.originalPublicationYear = other.originalPublicationYear == null ? null : other.originalPublicationYear.copy();
        this.originalPublicationMonth = other.originalPublicationMonth == null ? null : other.originalPublicationMonth.copy();
        this.originalPublicationDay = other.originalPublicationDay == null ? null : other.originalPublicationDay.copy();
    }

    @Override
    public BookCriteria copy() {
        return new BookCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getAuthor() {
        return author;
    }

    public void setAuthor(StringFilter author) {
        this.author = author;
    }

    public StringFilter getYear() {
        return year;
    }

    public void setYear(StringFilter year) {
        this.year = year;
    }

    public StringFilter getPublisher() {
        return publisher;
    }

    public void setPublisher(StringFilter publisher) {
        this.publisher = publisher;
    }

    public StringFilter getPages() {
        return pages;
    }

    public void setPages(StringFilter pages) {
        this.pages = pages;
    }

    public StringFilter getLanguage() {
        return language;
    }

    public void setLanguage(StringFilter language) {
        this.language = language;
    }

    public StringFilter getTopic() {
        return topic;
    }

    public void setTopic(StringFilter topic) {
        this.topic = topic;
    }

    public StringFilter getIdentifier() {
        return identifier;
    }

    public void setIdentifier(StringFilter identifier) {
        this.identifier = identifier;
    }

    public StringFilter getFilesize() {
        return filesize;
    }

    public void setFilesize(StringFilter filesize) {
        this.filesize = filesize;
    }

    public StringFilter getExtension() {
        return extension;
    }

    public void setExtension(StringFilter extension) {
        this.extension = extension;
    }

    public StringFilter getMd5() {
        return md5;
    }

    public void setMd5(StringFilter md5) {
        this.md5 = md5;
    }

    public StringFilter getCoverurl() {
        return coverurl;
    }

    public void setCoverurl(StringFilter coverurl) {
        this.coverurl = coverurl;
    }

    public FloatFilter getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(FloatFilter averageRating) {
        this.averageRating = averageRating;
    }

    public IntegerFilter getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(IntegerFilter ratingCount) {
        this.ratingCount = ratingCount;
    }

    public IntegerFilter getOriginalPublicationYear() {
        return originalPublicationYear;
    }

    public void setOriginalPublicationYear(IntegerFilter originalPublicationYear) {
        this.originalPublicationYear = originalPublicationYear;
    }

    public IntegerFilter getOriginalPublicationMonth() {
        return originalPublicationMonth;
    }

    public void setOriginalPublicationMonth(IntegerFilter originalPublicationMonth) {
        this.originalPublicationMonth = originalPublicationMonth;
    }

    public IntegerFilter getOriginalPublicationDay() {
        return originalPublicationDay;
    }

    public void setOriginalPublicationDay(IntegerFilter originalPublicationDay) {
        this.originalPublicationDay = originalPublicationDay;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BookCriteria that = (BookCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(author, that.author) &&
            Objects.equals(year, that.year) &&
            Objects.equals(publisher, that.publisher) &&
            Objects.equals(pages, that.pages) &&
            Objects.equals(language, that.language) &&
            Objects.equals(topic, that.topic) &&
            Objects.equals(identifier, that.identifier) &&
            Objects.equals(filesize, that.filesize) &&
            Objects.equals(extension, that.extension) &&
            Objects.equals(md5, that.md5) &&
            Objects.equals(coverurl, that.coverurl) &&
            Objects.equals(averageRating, that.averageRating) &&
            Objects.equals(ratingCount, that.ratingCount) &&
            Objects.equals(originalPublicationYear, that.originalPublicationYear) &&
            Objects.equals(originalPublicationMonth, that.originalPublicationMonth) &&
            Objects.equals(originalPublicationDay, that.originalPublicationDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        author,
        year,
        publisher,
        pages,
        language,
        topic,
        identifier,
        filesize,
        extension,
        md5,
        coverurl,
        averageRating,
        ratingCount,
        originalPublicationYear,
        originalPublicationMonth,
        originalPublicationDay
        );
    }

    @Override
    public String toString() {
        return "BookCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (author != null ? "author=" + author + ", " : "") +
                (year != null ? "year=" + year + ", " : "") +
                (publisher != null ? "publisher=" + publisher + ", " : "") +
                (pages != null ? "pages=" + pages + ", " : "") +
                (language != null ? "language=" + language + ", " : "") +
                (topic != null ? "topic=" + topic + ", " : "") +
                (identifier != null ? "identifier=" + identifier + ", " : "") +
                (filesize != null ? "filesize=" + filesize + ", " : "") +
                (extension != null ? "extension=" + extension + ", " : "") +
                (md5 != null ? "md5=" + md5 + ", " : "") +
                (coverurl != null ? "coverurl=" + coverurl + ", " : "") +
                (averageRating != null ? "averageRating=" + averageRating + ", " : "") +
                (ratingCount != null ? "ratingCount=" + ratingCount + ", " : "") +
                (originalPublicationYear != null ? "originalPublicationYear=" + originalPublicationYear + ", " : "") +
                (originalPublicationMonth != null ? "originalPublicationMonth=" + originalPublicationMonth + ", " : "") +
                (originalPublicationDay != null ? "originalPublicationDay=" + originalPublicationDay + ", " : "") +
            "}";
    }

}
