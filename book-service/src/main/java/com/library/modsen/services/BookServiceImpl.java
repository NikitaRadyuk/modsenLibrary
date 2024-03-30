package com.library.modsen.services;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import test.modsen.library.core.dto.BookInfoDTO;
import test.modsen.library.core.dto.CreateBookDTO;
import test.modsen.library.core.entities.BookEntity;
import test.modsen.library.core.enums.Status;
import test.modsen.library.core.exceptions.exceptions.CustomEntityNotFoundException;
import test.modsen.library.core.exceptions.exceptions.CustomValidationException;
import test.modsen.library.repository.BookRepository;
import test.modsen.library.services.api.IBookService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class BookServiceImpl implements IBookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<BookEntity> getPage(Pageable pageable) {
        return this.bookRepository.findAll(pageable);
    }

    @Override
    public BookInfoDTO findByUuid(UUID uuid) {
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
    public BookInfoDTO findByISBN(String isbn) {
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
    public void createBook(CreateBookDTO createBookDTO) {
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
        } catch (DataAccessException e){
            throw new CustomValidationException();
        }
    }

    @Override
    public void updateBookByUUID(UUID uuid, CreateBookDTO createBookDTO) {
        BookEntity bookEntity = modelMapper.map(findByUuid(uuid), BookEntity.class);
        bookEntity.setDtUpdate(LocalDateTime.now());
        bookEntity.setIsbn(createBookDTO.getIsbn());
        bookEntity.setTitle(createBookDTO.getTitle());
        bookEntity.setAuthor(createBookDTO.getAuthor());
        bookEntity.setGenre(createBookDTO.getGenre());
        bookEntity.setDescription(createBookDTO.getDescription());

        try{
            this.bookRepository.saveAndFlush(bookEntity);
        } catch (CustomValidationException e){
            throw new CustomValidationException();
        }

    }

    @Override
    public void deleteBookByUUID(UUID uuid) {
        try{
            this.bookRepository.deleteById(uuid);
        } catch (CustomEntityNotFoundException e){
            throw new CustomEntityNotFoundException(uuid);
        }
    }
}
