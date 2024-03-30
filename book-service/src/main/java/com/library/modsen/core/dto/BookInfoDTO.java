package com.library.modsen.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.library.modsen.core.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BookInfoDTO {
    @JsonProperty("id")
    private UUID uuid;
    @JsonProperty("isbn")
    private String isbn;
    @JsonProperty("title")
    private String title;
    @JsonProperty("genre")
    private String genre;
    @JsonProperty("description")
    private String description;
    @JsonProperty("author")
    private String author;
    @JsonProperty("status")
    private Status status;
}
