package test.modsen.libraryservice.repository;

import com.library.modsen.core.entities.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import test.modsen.libraryservice.core.entity.BookRecordEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LibraryRepository extends JpaRepository<BookRecordEntity, UUID> {
    @Query("select BookEntity from BookEntity where BookEntity.status =: FREE")
    Page<BookEntity> getFreeBookRecordEntities(Pageable pageable);
    Optional<BookRecordEntity> getBookRecordEntityByUuid(UUID uuid);
}
