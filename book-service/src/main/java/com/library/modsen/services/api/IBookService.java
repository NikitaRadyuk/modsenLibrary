package com.library.modsen.services.api;

import com.library.modsen.core.dto.BookInfoDTO;
import com.library.modsen.core.dto.CreateBookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IBookService {
    Page<BookInfoDTO> getPage(Pageable pageable);
    BookInfoDTO findByUuid(UUID uuid);
    BookInfoDTO findByISBN(String isbn);
    void createBook(CreateBookDTO createBookDTO);
    void updateBookByUUID(UUID uuid, CreateBookDTO createBookDTO);
    void deleteBookByUUID(UUID uuid);
}
