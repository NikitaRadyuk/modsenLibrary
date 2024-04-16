package test.modsen.libraryservice.controller;

import com.library.modsen.core.dto.BookInfoDTO;
import com.library.modsen.core.entities.BookEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import test.modsen.libraryservice.core.dto.BookFindDTO;
import test.modsen.libraryservice.core.dto.BookBorrowDTO;
import test.modsen.libraryservice.core.dto.BookReturnDTO;
import test.modsen.libraryservice.services.api.ILibraryService;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/library")
@Slf4j
public class LibraryController {
    private final ILibraryService libraryService;

    @GetMapping("/available-books")
    public Page<BookInfoDTO> getFreeBooksPage(@RequestParam(defaultValue = "0") Integer number,
                                                @RequestParam(defaultValue = "10") Integer size){
        log.info("Getting available books from library");
        Pageable pageable = PageRequest.of(number, size);
        Page<BookInfoDTO> page = this.libraryService.getFreeBooksPage(pageable);
        return page;
    }

    @PostMapping("/books/{uuid}")
    public ResponseEntity<String> getBook(@RequestBody BookReturnDTO bookReturnDTO){
        log.info("Get book from library");
        libraryService.getBookFromLibrary(bookReturnDTO);
        return new ResponseEntity<>("Book was taken", HttpStatus.OK);
    }

    @PostMapping("/return/{uuid}")
    public ResponseEntity<String> returnBook(@PathVariable String uuid){
        log.info("Return book to library");
        libraryService.returnBookToLibrary(UUID.fromString(uuid));
        return new ResponseEntity<>("Book was returned", HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> createBookRecord(@RequestBody BookFindDTO bookFindDTO){
        log.info("Creating new book record in the library");
        libraryService.createBookRecord(bookFindDTO);
        return new ResponseEntity<>("Book record was created", HttpStatus.OK);
    }

    @PutMapping("/books")
    public ResponseEntity<String> updateBookRecord(@RequestBody BookBorrowDTO bookBorrowDTO){
        log.info("Updating book record in the library");
        libraryService.updateBook(bookBorrowDTO);
        return new ResponseEntity<>("Book record was updated", HttpStatus.OK);
    }

}
