package test.modsen.libraryservice.services.impl;

import com.library.modsen.core.dto.BookInfoDTO;
import com.library.modsen.core.entities.BookEntity;
import com.library.modsen.core.enums.Status;
import com.library.modsen.repository.BookRepository;
import com.library.modsen.services.api.IBookService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.modsen.libraryservice.core.dto.BookFindDTO;
import test.modsen.libraryservice.core.dto.BookBorrowDTO;
import test.modsen.libraryservice.core.dto.BookReturnDTO;
import test.modsen.libraryservice.core.entity.BookRecordEntity;
import test.modsen.libraryservice.core.exceptions.BookNotFoundException;
import test.modsen.libraryservice.repository.LibraryRepository;
import test.modsen.libraryservice.services.api.ILibraryService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class LibraryServiceImpl implements ILibraryService {
    private final LibraryRepository libraryRepository;
    private final ModelMapper modelMapper;
    private final IBookService bookService;
    private final BookRepository bookRepository;

    public LibraryServiceImpl(LibraryRepository libraryRepository, ModelMapper modelMapper, IBookService bookService, BookRepository bookRepository) {
        this.libraryRepository = libraryRepository;
        this.modelMapper = modelMapper;
        this.bookService = bookService;
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<BookInfoDTO> getFreeBooksPage(Pageable pageable) {
        log.info("Getting all free books");
        Page<BookEntity> page = libraryRepository.getFreeBookRecordEntities(pageable);
        return page.map(LibraryServiceImpl::setPageObject);
    }

    @Transactional
    @Override
    public void getBookFromLibrary(BookReturnDTO bookRecordGetDTO){
        BookRecordEntity bookRecord = modelMapper.map(bookRecordGetDTO, BookRecordEntity.class);

        BookEntity book = modelMapper.map(bookService.findByUuid(bookRecordGetDTO.getBookUUID()), BookEntity.class);

        book.setStatus(Status.BUSY);

        bookRepository.save(book);
        libraryRepository.save(bookRecord);
        log.info("The book was taken");
    }

    @Transactional
    @Override
    public void returnBookToLibrary(UUID bookUUID) {
        BookRecordEntity bookRecordEntity = new BookRecordEntity();
        bookRecordEntity.setUuid(bookUUID);
        bookRecordEntity.setReturnTime(LocalDateTime.now());

        BookEntity book = modelMapper.map(bookService.findByUuid(bookUUID), BookEntity.class);

        book.setStatus(Status.BUSY);

        bookRepository.save(book);
        libraryRepository.save(bookRecordEntity);
        log.info("The book was returned");
    }

    @Override
    @Transactional
    public void createBookRecord(BookFindDTO bookFindDTO) {
        if(libraryRepository.existsById(bookFindDTO.getBookUUID())){
        log.info("Adding new book to library");
        BookRecordEntity bookRecord = new BookRecordEntity();
        bookRecord.setUuid(bookFindDTO.getBookUUID());
        bookRecord.setGetTime(null);
        bookRecord.setReturnTime(null);

        libraryRepository.save(bookRecord);
        }
        else {
            log.info("The book with {} ID already exists in the library", bookFindDTO.getBookUUID());
            throw new BookNotFoundException();
        }
    }

    @Transactional
    @Override
    public void updateBook(BookBorrowDTO bookBorrowDTO) {
        log.info("Trying to update book with id: {} record", bookBorrowDTO.getBookUUID());

        if(libraryRepository.existsById(bookBorrowDTO.getBookUUID())) {
            BookRecordEntity bookRecord = libraryRepository.getBookRecordEntityByUuid(bookBorrowDTO.getBookUUID());
            bookRecord.setReturnTime(bookBorrowDTO.getReturnTime());
            bookRecord.setGetTime(bookBorrowDTO.getGetTime());

            libraryRepository.save(bookRecord);
            log.info("The book has changed");
        } else{
            log.info("The book(with ID: {}) record doesn't exists", bookBorrowDTO.getBookUUID());
            throw new BookNotFoundException();
        }
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
