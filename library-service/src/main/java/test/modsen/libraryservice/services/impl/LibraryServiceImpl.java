package test.modsen.libraryservice.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.modsen.libraryservice.core.dto.BookFindDTO;
import test.modsen.libraryservice.core.dto.BookRecordDTO;
import test.modsen.libraryservice.core.entity.BookRecordEntity;
import test.modsen.libraryservice.core.exceptions.BookNotFoundException;
import test.modsen.libraryservice.repository.LibraryRepository;
import test.modsen.libraryservice.services.api.ILibraryService;

@Service
@Slf4j
public class LibraryServiceImpl implements ILibraryService {
    private final LibraryRepository libraryRepository;
    private final ModelMapper modelMapper;

    public LibraryServiceImpl(LibraryRepository libraryRepository, ModelMapper modelMapper) {
        this.libraryRepository = libraryRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<BookRecordEntity> getFreeBooksPage() {
        log.info("Getting all free books");
        return libraryRepository.getFreeBookRecordEntities();
    }

    @Override
    @Transactional
    public BookRecordDTO createBookRecord(BookFindDTO bookFindDTO) {
        if(libraryRepository.existsById(bookFindDTO.getBookUUID())){
        log.info("Adding new book to library");
        BookRecordEntity bookRecord = new BookRecordEntity();
        bookRecord.setUuid(bookFindDTO.getBookUUID());
        bookRecord.setGetTime(null);
        bookRecord.setReturnTime(null);

        libraryRepository.saveAndFlush(bookRecord);
        BookRecordDTO bookRecordDTO = modelMapper.map(bookRecord, BookRecordDTO.class);
        return bookRecordDTO;
        }
        else {
            log.info("The book with {} ID already exists in the library", bookFindDTO.getBookUUID());
            throw new BookNotFoundException();
        }
    }

    @Override
    public void updateBook(BookRecordDTO bookRecordDTO) {
        log.info("Trying to update book with id: {} record", bookRecordDTO.getBookUUID());

        if(libraryRepository.existsById(bookRecordDTO.getBookUUID())) {
            BookRecordEntity bookRecord = libraryRepository.getBookRecordEntityByUuid(bookRecordDTO.getBookUUID());
            bookRecord.setReturnTime(bookRecordDTO.getReturnTime());
            bookRecord.setGetTime(bookRecordDTO.getGetTime());

            libraryRepository.saveAndFlush(bookRecord);
            log.info("The book has changed");
        } else{
            log.info("The book(with ID: {}) record doesn't exists", bookRecordDTO.getBookUUID());
            throw new BookNotFoundException();
        }
    }
}
