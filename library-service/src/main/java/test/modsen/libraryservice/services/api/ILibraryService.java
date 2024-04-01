package test.modsen.libraryservice.services.api;

import com.library.modsen.core.entities.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import test.modsen.libraryservice.core.dto.BookFindDTO;
import test.modsen.libraryservice.core.dto.BookRecordDTO;
import test.modsen.libraryservice.core.dto.BookRecordGetDTO;

import java.util.UUID;

public interface ILibraryService{
    Page<BookEntity> getFreeBooksPage(Pageable pageable);
    BookRecordDTO createBookRecord(BookFindDTO bookFindDTO);
    void updateBook(BookRecordDTO bookRecordDTO);
    void getBookFromLibrary(BookRecordGetDTO bookRecordGetDTO);
    void returnBookToLibrary(UUID bookUUID);
}
