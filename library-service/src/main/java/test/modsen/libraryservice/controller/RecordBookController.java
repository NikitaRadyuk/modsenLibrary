package test.modsen.libraryservice.controller;

import com.library.modsen.core.dto.BookInfoDTO;
import com.library.modsen.core.entities.BookEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import test.modsen.libraryservice.core.dto.BookFindDTO;
import test.modsen.libraryservice.core.dto.BookRecordDTO;
import test.modsen.libraryservice.core.dto.BookRecordGetDTO;
import test.modsen.libraryservice.services.api.ILibraryService;

import java.util.UUID;


@RestController
@RequestMapping(value = "api/v1/library")
@Slf4j
public class RecordBookController {
    private final ILibraryService libraryService;

    public RecordBookController(ILibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/freeBooks")
    @ResponseBody
    public Page<BookInfoDTO> getFreeBooksPage(@RequestParam(defaultValue = "0") Integer number,
                                                @RequestParam(defaultValue = "10") Integer size){
        log.info("Getting free books from library");
        Pageable pageable = PageRequest.of(number, size);
        Page<BookEntity> page = this.libraryService.getFreeBooksPage(pageable);
        return page.map(RecordBookController::setPageObject);
    }

    @PostMapping("/get/{uuid}")
    @ResponseBody
    public ResponseEntity<String> getBook(@RequestBody BookRecordGetDTO bookRecordGetDTO){
        log.info("Get book from library");
        libraryService.getBookFromLibrary(bookRecordGetDTO);
        return new ResponseEntity<>("Book was taken", HttpStatus.OK);
    }

    @PostMapping("/return/{uuid}")
    @ResponseBody
    public ResponseEntity<String> returnBook(@PathVariable String bookUuid){
        log.info("Return book to library");
        libraryService.returnBookToLibrary(UUID.fromString(bookUuid));
        return new ResponseEntity<>("Book was returned", HttpStatus.OK);
    }

    @PostMapping("/newRecord")
    @ResponseBody
    public ResponseEntity<String> createBookRecord(@RequestBody BookFindDTO bookFindDTO){
        log.info("Creating new book record in the library");
        libraryService.createBookRecord(bookFindDTO);
        return new ResponseEntity<>("Book record was created", HttpStatus.OK);
    }

    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<String> updateBookRecord(@RequestBody BookRecordDTO bookRecordDTO){
        log.info("Updating book record in the library");
        libraryService.updateBook(bookRecordDTO);
        return new ResponseEntity<>("Book record was created", HttpStatus.OK);
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
