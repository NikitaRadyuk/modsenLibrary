package com.library.modsen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import test.modsen.library.core.dto.BookInfoDTO;
import test.modsen.library.core.entities.BookEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, UUID> {
    @Query("SELECT new test.modsen.library.core.dto.BookInfoDTO(u.uuid, u.isbn, u.title, u.genre, u.description, u.author, u.status) FROM BookEntity AS u WHERE u.uuid = :uuid")
    Optional<BookInfoDTO> findByUuid(UUID uuid);
    @Query("SELECT new test.modsen.library.core.dto.BookInfoDTO(u.uuid, u.isbn, u.title, u.genre, u.description, u.author, u.status) FROM BookEntity AS u WHERE u.isbn = :isbn")
    Optional<BookInfoDTO> findByIsbn(String isbn);
}
