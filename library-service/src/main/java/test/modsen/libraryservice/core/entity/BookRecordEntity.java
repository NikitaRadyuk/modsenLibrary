package test.modsen.libraryservice.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(schema = "modsen_library", name = "book_record")
public class BookRecordEntity {
    @Id
    private UUID uuid;
    @Column(name = "book_uuid")
    private UUID bookUuid;
    @Column(name = "get_time")
    private LocalDateTime getTime;
    @Column(name = "return_time")
    private LocalDateTime returnTime;

    public BookRecordEntity() {
    }

    public BookRecordEntity(UUID uuid, LocalDateTime getTime, LocalDateTime returnTime) {
        this.uuid = uuid;
        this.getTime = getTime;
        this.returnTime = returnTime;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getGetTime() {
        return getTime;
    }

    public void setGetTime(LocalDateTime getTime) {
        this.getTime = getTime;
    }

    public LocalDateTime getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(LocalDateTime returnTime) {
        this.returnTime = returnTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookRecordEntity that = (BookRecordEntity) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(getTime, that.getTime) && Objects.equals(returnTime, that.returnTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, getTime, returnTime);
    }

    @Override
    public String toString() {
        return "BookRecordEntity{" +
                "uuid=" + uuid +
                ", getTime=" + getTime +
                ", returnTime=" + returnTime +
                '}';
    }
}
