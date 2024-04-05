package test.modsen.libraryservice.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRecordDTO {
    @JsonProperty(value = "book_uuid")
    private UUID bookUUID;
    @JsonProperty(value = "get_time")
    private LocalDateTime getTime;
    @JsonProperty(value = "return_time")
    private LocalDateTime returnTime;
}
