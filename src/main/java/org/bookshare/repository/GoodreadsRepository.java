package org.bookshare.repository;
import org.bookshare.domain.Goodreads;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Goodreads entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GoodreadsRepository extends JpaRepository<Goodreads, Long>, JpaSpecificationExecutor<Goodreads> {

}
