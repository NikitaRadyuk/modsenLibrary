package com.library.modsen.httpclients;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import test.modsen.libraryservice.core.dto.BookRecordDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class RecordHttpClient {

    public ObjectMapper objectMapper;

    public BookRecordDTO sendRequestToCreateDTO(BookRecordDTO bookRecordDTO){
        try {
            String body = objectMapper.writeValueAsString(bookRecordDTO);
            HttpClient httpClient = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8081/"))
                    .headers(
                            "Content-Type", APPLICATION_JSON_VALUE
                    )
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), BookRecordDTO.class);
        } catch (Exception exception) {
            throw new RuntimeException("Error while sending request to library-service: " + exception);
        }
    }
}
