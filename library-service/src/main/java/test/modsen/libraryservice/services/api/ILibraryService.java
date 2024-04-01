package test.modsen.libraryservice.services.api;

import org.springframework.data.domain.Page;
import test.modsen.libraryservice.core.dto.BookFindDTO;
import test.modsen.libraryservice.core.dto.BookRecordDTO;
import test.modsen.libraryservice.core.entity.BookRecordEntity;

import java.util.UUID;

public interface ILibraryService{
    Page<BookRecordEntity> getFreeBooksPage();
    BookRecordDTO createBookRecord(BookFindDTO bookFindDTO);
    void updateBook(BookRecordDTO bookRecordDTO);
}
