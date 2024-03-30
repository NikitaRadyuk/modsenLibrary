package com.library.modsen.services.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import test.modsen.library.core.dto.BookInfoDTO;
import test.modsen.library.core.dto.CreateBookDTO;
import test.modsen.library.core.entities.BookEntity;

import java.util.UUID;

@FeignClient(name = "library-service")
public interface IBookService {
    Page<BookEntity> getPage(Pageable pageable);
    BookInfoDTO findByUuid(UUID uuid);
    BookInfoDTO findByISBN(String isbn);
    void createBook(CreateBookDTO createBookDTO);
    void updateBookByUUID(UUID uuid, CreateBookDTO createBookDTO);
    void deleteBookByUUID(UUID uuid);
}
