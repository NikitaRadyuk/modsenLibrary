package test.modsen.libraryservice.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookUpdateDTO {
    @JsonProperty("book_uuid")
    private UUID bookUUID;
}
