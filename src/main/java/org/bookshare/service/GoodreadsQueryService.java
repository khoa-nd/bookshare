package org.bookshare.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import org.bookshare.domain.Goodreads;
import org.bookshare.domain.*; // for static metamodels
import org.bookshare.repository.GoodreadsRepository;
import org.bookshare.service.dto.GoodreadsCriteria;
import org.bookshare.service.dto.GoodreadsDTO;
import org.bookshare.service.mapper.GoodreadsMapper;

/**
 * Service for executing complex queries for {@link Goodreads} entities in the database.
 * The main input is a {@link GoodreadsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GoodreadsDTO} or a {@link Page} of {@link GoodreadsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GoodreadsQueryService extends QueryService<Goodreads> {

    private final Logger log = LoggerFactory.getLogger(GoodreadsQueryService.class);

    private final GoodreadsRepository goodreadsRepository;

    private final GoodreadsMapper goodreadsMapper;

    public GoodreadsQueryService(GoodreadsRepository goodreadsRepository, GoodreadsMapper goodreadsMapper) {
        this.goodreadsRepository = goodreadsRepository;
        this.goodreadsMapper = goodreadsMapper;
    }

    /**
     * Return a {@link List} of {@link GoodreadsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GoodreadsDTO> findByCriteria(GoodreadsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Goodreads> specification = createSpecification(criteria);
        return goodreadsMapper.toDto(goodreadsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link GoodreadsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GoodreadsDTO> findByCriteria(GoodreadsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Goodreads> specification = createSpecification(criteria);
        return goodreadsRepository.findAll(specification, page)
            .map(goodreadsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GoodreadsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Goodreads> specification = createSpecification(criteria);
        return goodreadsRepository.count(specification);
    }

    /**
     * Function to convert {@link GoodreadsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Goodreads> createSpecification(GoodreadsCriteria criteria) {
        Specification<Goodreads> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Goodreads_.id));
            }
            if (criteria.getBookId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBookId(), Goodreads_.bookId));
            }
            if (criteria.getgRBookId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getgRBookId(), Goodreads_.gRBookId));
            }
            if (criteria.getRatingCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRatingCount(), Goodreads_.ratingCount));
            }
            if (criteria.getTextReviewsCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTextReviewsCount(), Goodreads_.textReviewsCount));
            }
            if (criteria.getAverageRating() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAverageRating(), Goodreads_.averageRating));
            }
            if (criteria.getLastUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdated(), Goodreads_.lastUpdated));
            }
        }
        return specification;
    }
}
