package org.bookshare.service;

import org.bookshare.domain.Goodreads;
import org.bookshare.repository.GoodreadsRepository;
import org.bookshare.service.dto.GoodreadsDTO;
import org.bookshare.service.mapper.GoodreadsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Goodreads}.
 */
@Service
@Transactional
public class GoodreadsService {

    private final Logger log = LoggerFactory.getLogger(GoodreadsService.class);

    private final GoodreadsRepository goodreadsRepository;

    private final GoodreadsMapper goodreadsMapper;

    public GoodreadsService(GoodreadsRepository goodreadsRepository, GoodreadsMapper goodreadsMapper) {
        this.goodreadsRepository = goodreadsRepository;
        this.goodreadsMapper = goodreadsMapper;
    }

    /**
     * Save a goodreads.
     *
     * @param goodreadsDTO the entity to save.
     * @return the persisted entity.
     */
    public GoodreadsDTO save(GoodreadsDTO goodreadsDTO) {
        log.debug("Request to save Goodreads : {}", goodreadsDTO);
        Goodreads goodreads = goodreadsMapper.toEntity(goodreadsDTO);
        goodreads = goodreadsRepository.save(goodreads);
        return goodreadsMapper.toDto(goodreads);
    }

    /**
     * Get all the goodreads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GoodreadsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Goodreads");
        return goodreadsRepository.findAll(pageable)
            .map(goodreadsMapper::toDto);
    }


    /**
     * Get one goodreads by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GoodreadsDTO> findOne(Long id) {
        log.debug("Request to get Goodreads : {}", id);
        return goodreadsRepository.findById(id)
            .map(goodreadsMapper::toDto);
    }

    /**
     * Delete the goodreads by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Goodreads : {}", id);
        goodreadsRepository.deleteById(id);
    }
}
