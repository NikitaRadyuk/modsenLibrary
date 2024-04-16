package com.library.modsen.controller;

import com.library.modsen.core.dto.BookInfoDTO;
import com.library.modsen.core.dto.CreateBookDTO;
import com.library.modsen.core.entities.BookEntity;
import com.library.modsen.services.api.IBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value ="api/v1/books")
@Slf4j
public class BookController {
    private final IBookService bookService;

    @GetMapping
    public Page<BookInfoDTO> getPage(@RequestParam(defaultValue = "0") Integer number,
                                     @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("Getting all books");
        Pageable pageable = PageRequest.of(number, size);
        Page<BookEntity> page = this.bookService.getPage(pageable);
        return page.map(BookController::setPageObject);
    }

    @GetMapping("/{uuid}")
    public BookInfoDTO getBook(@PathVariable("uuid") String uuid) {
        log.info("Getting book by id {}", uuid);
        return this.bookService.findByUuid(UUID.fromString(uuid));
    }

    @PostMapping("/add")
    public ResponseEntity<String> createBook(@RequestBody CreateBookDTO bookDTO) {
        log.info("Created a new book");
        this.bookService.createBook(bookDTO);
        return new ResponseEntity<>("Книга добавлена", HttpStatus.CREATED);
    }

    @GetMapping("/isbn/{isbn}")
    public BookInfoDTO getPage(@PathVariable("isbn")String isbn) {
        log.info("Getting book by isbn code {}", isbn);
        return this.bookService.findByISBN(isbn);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<String> updateBook(
            @PathVariable("uuid") UUID uuid,
            @RequestBody CreateBookDTO createBookDTO
    ){
        log.info("The book with ID {} was updated", uuid);
        this.bookService.updateBookByUUID(uuid, createBookDTO);
        return ResponseEntity.ok("Книга обновлена");
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> deleteBook(
            @PathVariable("uuid")UUID uuid
    ){
        log.info("The book with ID {} was deleted", uuid);
        this.bookService.deleteBookByUUID(uuid);
        return ResponseEntity.ok("Книга удалена");
    }

    private static BookInfoDTO setPageObject(BookEntity bookEntity){
        BookInfoDTO bookInfoDTO = new BookInfoDTO();
        bookInfoDTO.setStatus(bookEntity.getStatus())
                        .setDescription(bookEntity.getDescription())
                .setIsbn(bookEntity.getIsbn())
                .setUuid(bookEntity.getUuid())
                .setAuthor(bookEntity.getAuthor())
                .setGenre(bookEntity.getGenre())
                .setTitle(bookEntity.getTitle());
        return bookInfoDTO;
    }
}
