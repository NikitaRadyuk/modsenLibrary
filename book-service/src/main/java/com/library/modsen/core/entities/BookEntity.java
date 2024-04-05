package com.library.modsen.core.entities;

import com.library.modsen.core.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(schema = "modsen_library", name = "book")
public class BookEntity {
    @Id
    private UUID uuid;
    @Column(name = "isbn")
    private String isbn;
    private String genre;
    private String title;
    private String description;
    private String author;
    @Column(name = "dt_create")
    private LocalDateTime dtCreate;
    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;
    @Enumerated(EnumType.STRING)
    private Status status;

    public BookEntity() {
    }

    public BookEntity(UUID uuid, String isbn, String genre, String title, String description, String author, LocalDateTime dtCreate, LocalDateTime dtUpdate, Status status) {
        this.uuid = uuid;
        this.isbn = isbn;
        this.genre = genre;
        this.title = title;
        this.description = description;
        this.author = author;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.status = status;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookEntity that = (BookEntity) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(isbn, that.isbn) && Objects.equals(genre, that.genre) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(author, that.author) && Objects.equals(dtCreate, that.dtCreate) && Objects.equals(dtUpdate, that.dtUpdate) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, isbn, genre, title, description, author, dtCreate, dtUpdate, status);
    }

    @Override
    public String toString() {
        return "BookEntity{" +
                "uuid=" + uuid +
                ", isbn='" + isbn + '\'' +
                ", genre='" + genre + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", dtCreate=" + dtCreate +
                ", dtUpdate=" + dtUpdate +
                ", status=" + status +
                '}';
    }
}