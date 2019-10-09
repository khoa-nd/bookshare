package org.bookshare.domain;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Book.
 */
@Entity
@Table(name = "book")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Title")
    private String title;

    @Column(name = "Author")
    private String author;

    @Column(name = "Year")
    private String year;

    @Column(name = "Publisher")
    private String publisher;

    @Column(name = "Pages")
    private String pages;

    @Column(name = "Language")
    private String language;

    @Column(name = "Topic")
    private String topic;

    @Column(name = "Identifier")
    private String identifier;

    @Column(name = "Filesize")
    private String filesize;

    @Column(name = "Extension")
    private String extension;

    @Column(name = "MD5")
    private String md5;

    @Column(name = "Coverurl")
    private String coverurl;

    @Column(name = "AverageRating")
    private Float averageRating;

    @Column(name = "RatingCount")
    private Integer ratingCount;

    @Column(name = "OriginalPublicationYear")
    private Integer originalPublicationYear;

    @Column(name = "OriginalPublicationMonth")
    private Integer originalPublicationMonth;

    @Column(name = "OriginalPublicationDay")
    private Integer originalPublicationDay;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Book title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public Book author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public Book year(String year) {
        this.year = year;
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPublisher() {
        return publisher;
    }

    public Book publisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPages() {
        return pages;
    }

    public Book pages(String pages) {
        this.pages = pages;
        return this;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getLanguage() {
        return language;
    }

    public Book language(String language) {
        this.language = language;
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTopic() {
        return topic;
    }

    public Book topic(String topic) {
        this.topic = topic;
        return this;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Book identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getFilesize() {
        return filesize;
    }

    public Book filesize(String filesize) {
        this.filesize = filesize;
        return this;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public String getExtension() {
        return extension;
    }

    public Book extension(String extension) {
        this.extension = extension;
        return this;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getMd5() {
        return md5;
    }

    public Book md5(String md5) {
        this.md5 = md5;
        return this;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getCoverurl() {
        return coverurl;
    }

    public Book coverurl(String coverurl) {
        this.coverurl = coverurl;
        return this;
    }

    public void setCoverurl(String coverurl) {
        this.coverurl = coverurl;
    }

    public Float getAverageRating() {
        return averageRating;
    }

    public Book averageRating(Float averageRating) {
        this.averageRating = averageRating;
        return this;
    }

    public void setAverageRating(Float averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getRatingCount() {
        return ratingCount;
    }

    public Book ratingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
        return this;
    }

    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
    }

    public Integer getOriginalPublicationYear() {
        return originalPublicationYear;
    }

    public Book originalPublicationYear(Integer originalPublicationYear) {
        this.originalPublicationYear = originalPublicationYear;
        return this;
    }

    public void setOriginalPublicationYear(Integer originalPublicationYear) {
        this.originalPublicationYear = originalPublicationYear;
    }

    public Integer getOriginalPublicationMonth() {
        return originalPublicationMonth;
    }

    public Book originalPublicationMonth(Integer originalPublicationMonth) {
        this.originalPublicationMonth = originalPublicationMonth;
        return this;
    }

    public void setOriginalPublicationMonth(Integer originalPublicationMonth) {
        this.originalPublicationMonth = originalPublicationMonth;
    }

    public Integer getOriginalPublicationDay() {
        return originalPublicationDay;
    }

    public Book originalPublicationDay(Integer originalPublicationDay) {
        this.originalPublicationDay = originalPublicationDay;
        return this;
    }

    public void setOriginalPublicationDay(Integer originalPublicationDay) {
        this.originalPublicationDay = originalPublicationDay;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }
        return id != null && id.equals(((Book) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Book{" +
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
