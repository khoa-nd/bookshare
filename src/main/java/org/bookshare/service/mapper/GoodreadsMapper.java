package org.bookshare.service.mapper;

import org.bookshare.domain.*;
import org.bookshare.service.dto.GoodreadsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Goodreads} and its DTO {@link GoodreadsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GoodreadsMapper extends EntityMapper<GoodreadsDTO, Goodreads> {



    default Goodreads fromId(Long id) {
        if (id == null) {
            return null;
        }
        Goodreads goodreads = new Goodreads();
        goodreads.setId(id);
        return goodreads;
    }
}
