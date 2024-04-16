package com.library.modsen.repository;

import com.library.modsen.core.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, UUID> {
    @Query("SELECT new com.library.modsen.core.entities.BookEntity(u.uuid, u.isbn, u.genre, u.title,  u.description, u.author, u.dtCreate, u.dtUpdate, u.status) FROM BookEntity AS u WHERE u.uuid = :uuid")
    Optional<BookEntity> findByUuid(UUID uuid);
    @Query("SELECT new com.library.modsen.core.entities.BookEntity(u.uuid, u.isbn, u.genre, u.title,  u.description, u.author, u.dtCreate, u.dtUpdate, u.status) FROM BookEntity AS u WHERE u.isbn = :isbn")
    Optional<BookEntity> findByIsbn(String isbn);
}
