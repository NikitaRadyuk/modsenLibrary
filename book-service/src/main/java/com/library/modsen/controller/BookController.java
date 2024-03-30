package com.library.modsen.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.modsen.library.core.dto.BookInfoDTO;
import test.modsen.library.core.dto.CreateBookDTO;
import test.modsen.library.core.entities.BookEntity;
import test.modsen.library.services.api.IBookService;

import java.util.UUID;

@RestController
@RequestMapping(value ="/library")
@RequiredArgsConstructor
@Slf4j
public class BookController {
    private IBookService bookService;
    private final ModelMapper modelMapper;

    @GetMapping
    @ResponseBody
    public Page<BookInfoDTO> getPage(@RequestParam(defaultValue = "0") Integer number,
                                     @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("Getting all books");
        Pageable pageable = PageRequest.of(number, size);
        Page<BookEntity> page = this.bookService.getPage(pageable);
        return page.map(BookController::setPageObject);
    }

    @GetMapping("/book/{uuid}")
    @ResponseBody
    public BookInfoDTO getBook(@PathVariable("uuid") String uuid) {
        log.info("Getting book by id {}", uuid);
        BookInfoDTO bookInfoDTO = this.bookService.findByUuid(UUID.fromString(uuid));
        return modelMapper.map(bookInfoDTO, BookInfoDTO.class);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> createBook(@RequestBody CreateBookDTO bookDTO) {
        log.info("Created a new book");
        this.bookService.createBook(bookDTO);
        return new ResponseEntity<>("Книга добавлена", HttpStatus.CREATED);
    }

    @GetMapping("/book/isbn/{isbn}")
    @ResponseBody
    public BookInfoDTO getPage(@PathVariable("isbn")String isbn) {
        log.info("Getting book by isbn code {}", isbn);
        return modelMapper.map(this.bookService.findByISBN(isbn), BookInfoDTO.class);
    }

    @PutMapping("/book/update/{uuid}")
    @ResponseBody
    public ResponseEntity<String> updateBook(
            @PathVariable("uuid") UUID uuid,
            @RequestBody CreateBookDTO createBookDTO
    ){
        log.info("The book with ID {} was updated", uuid);
        this.bookService.updateBookByUUID(uuid, createBookDTO);
        return ResponseEntity.ok("Книга обновлена");
    }

    @DeleteMapping("/book/delete/{uuid}")
    @ResponseBody
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
