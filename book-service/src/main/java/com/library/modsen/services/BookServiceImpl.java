package com.library.modsen.services;

import com.library.modsen.core.dto.BookInfoDTO;
import com.library.modsen.core.dto.CreateBookDTO;
import com.library.modsen.core.entities.BookEntity;
import com.library.modsen.core.enums.Status;
import com.library.modsen.core.exceptions.exceptions.CustomEntityNotFoundException;
import com.library.modsen.core.exceptions.exceptions.CustomValidationException;
import com.library.modsen.repository.BookRepository;
import com.library.modsen.services.api.IBookService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class BookServiceImpl implements IBookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<BookInfoDTO> getPage(Pageable pageable) {
        log.info("Getting all existing books");
        Page<BookEntity> page = this.bookRepository.findAll(pageable);
        return page.map(BookServiceImpl::setPageObjects);
    }

    @Override
    @Transactional(readOnly = true)
    public BookInfoDTO findByUuid(UUID uuid) {
        log.info("Getting book by id: {}", uuid);
        try{
            if(this.bookRepository.findByUuid(uuid).isPresent()) {
                return this.bookRepository.findByUuid(uuid).get();
            } else throw new CustomEntityNotFoundException(uuid);
        }
        catch (CustomValidationException e){
            throw new CustomValidationException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public BookInfoDTO findByISBN(String isbn) {
        log.info("Getting book by isbn code: {}", isbn);
        try{
            if (this.bookRepository.findByIsbn(isbn).isPresent()) {
                return this.bookRepository.findByIsbn(isbn).get();
            } else throw new CustomEntityNotFoundException(isbn);
        }
        catch (CustomValidationException e){
            throw new CustomValidationException();
        }
    }

    @Override
    @Transactional
    public void createBook(CreateBookDTO createBookDTO) {
        log.info("Trying to create a new book");

        BookEntity bookEntity = new BookEntity();

        bookEntity.setUuid(UUID.randomUUID());
        bookEntity.setDtCreate(LocalDateTime.now());
        bookEntity.setDtUpdate(bookEntity.getDtCreate());
        bookEntity.setIsbn(createBookDTO.getIsbn());
        bookEntity.setTitle(createBookDTO.getTitle());
        bookEntity.setAuthor(createBookDTO.getAuthor());
        bookEntity.setGenre(createBookDTO.getGenre());
        bookEntity.setDescription(createBookDTO.getDescription());
        bookEntity.setStatus(Status.FREE);

        try{
            this.bookRepository.saveAndFlush(bookEntity);
            log.info("This book was created.");
        } catch (DataAccessException e){
            throw new CustomValidationException();
        }

    }

    @Override
    @Transactional
    public void updateBookByUUID(UUID uuid, CreateBookDTO createBookDTO) {
        log.info("Try to update this book: {}", uuid);

        BookEntity bookEntity = modelMapper.map(findByUuid(uuid), BookEntity.class);
        bookEntity.setDtUpdate(LocalDateTime.now());
        bookEntity.setIsbn(createBookDTO.getIsbn());
        bookEntity.setTitle(createBookDTO.getTitle());
        bookEntity.setAuthor(createBookDTO.getAuthor());
        bookEntity.setGenre(createBookDTO.getGenre());
        bookEntity.setDescription(createBookDTO.getDescription());

        try{
            this.bookRepository.saveAndFlush(bookEntity);
            log.info("This book was already updated.");
        } catch (CustomValidationException e){
            throw new CustomValidationException();
        }

    }

    @Override
    public void deleteBookByUUID(UUID uuid) {
        log.info("Try to delete this book: {}", uuid);
        try{
            this.bookRepository.deleteById(uuid);
            log.info("The book was already deleted.");
        } catch (CustomEntityNotFoundException e){
            throw new CustomEntityNotFoundException(uuid);
        }
    }

    private static BookInfoDTO setPageObjects(BookEntity bookEntity){
        BookInfoDTO book = new BookInfoDTO();
        book.setStatus(bookEntity.getStatus());
        book.setDescription(bookEntity.getDescription());
        book.setIsbn(bookEntity.getIsbn());
        book.setUuid(bookEntity.getUuid());
        book.setAuthor(bookEntity.getAuthor());
        book.setGenre(bookEntity.getGenre());
        book.setTitle(bookEntity.getTitle());
        return book;
    }
}
