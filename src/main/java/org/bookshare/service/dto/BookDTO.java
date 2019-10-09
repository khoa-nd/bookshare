package org.bookshare.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.bookshare.domain.Book} entity.
 */
public class BookDTO implements Serializable {

    private Long id;

    private String title;

    private String author;

    private String year;

    private String publisher;

    private String pages;

    private String language;

    private String topic;

    private String identifier;

    private String filesize;

    private String extension;

    private String md5;

    private String coverurl;

    private Float averageRating;

    private Integer ratingCount;

    private Integer originalPublicationYear;

    private Integer originalPublicationMonth;

    private Integer originalPublicationDay;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getCoverurl() {
        return coverurl;
    }

    public void setCoverurl(String coverurl) {
        this.coverurl = coverurl;
    }

    public Float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Float averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
    }

    public Integer getOriginalPublicationYear() {
        return originalPublicationYear;
    }

    public void setOriginalPublicationYear(Integer originalPublicationYear) {
        this.originalPublicationYear = originalPublicationYear;
    }

    public Integer getOriginalPublicationMonth() {
        return originalPublicationMonth;
    }

    public void setOriginalPublicationMonth(Integer originalPublicationMonth) {
        this.originalPublicationMonth = originalPublicationMonth;
    }

    public Integer getOriginalPublicationDay() {
        return originalPublicationDay;
    }

    public void setOriginalPublicationDay(Integer originalPublicationDay) {
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

        BookDTO bookDTO = (BookDTO) o;
        if (bookDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bookDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BookDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", author='" + getAuthor() + "'" +
            ", year='" + getYear() + "'" +
            ", publisher='" + getPublisher() + "'" +
            ", pages='" + getPages() + "'" +
            ", language='" + getLanguage() + "'" +
            ", topic='" + getTopic() + "'" +
            ", identifier='" + getIdentifier() + "'" +
            ", filesize='" + getFilesize() + "'" +
            ", extension='" + getExtension() + "'" +
            ", md5='" + getMd5() + "'" +
            ", coverurl='" + getCoverurl() + "'" +
            ", averageRating=" + getAverageRating() +
            ", ratingCount=" + getRatingCount() +
            ", originalPublicationYear=" + getOriginalPublicationYear() +
            ", originalPublicationMonth=" + getOriginalPublicationMonth() +
            ", originalPublicationDay=" + getOriginalPublicationDay() +
            "}";
    }
}
