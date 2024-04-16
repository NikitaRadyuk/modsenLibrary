package test.modsen.libraryservice.services.api;

import com.library.modsen.core.dto.BookInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import test.modsen.libraryservice.core.dto.BookFindDTO;
import test.modsen.libraryservice.core.dto.BookBorrowDTO;
import test.modsen.libraryservice.core.dto.BookReturnDTO;

import java.util.UUID;

public interface ILibraryService{
    Page<BookInfoDTO> getFreeBooksPage(Pageable pageable);
    void createBookRecord(BookFindDTO bookFindDTO);
    void updateBook(BookBorrowDTO bookBorrowDTO);
    void getBookFromLibrary(BookReturnDTO bookRecordGetDTO);
    void returnBookToLibrary(UUID bookUUID);
}
