package com.library.modsen.httpclients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import test.modsen.libraryservice.core.dto.BookFindDTO;

@Component
@Slf4j
public class LibraryFallback implements LibraryFeignClient{

    @Override
    public ResponseEntity<String> sendRequestToCreateRecord(BookFindDTO bookFindDTO) {
        log.error("Cannot get access to the library-service...");
        return ResponseEntity.internalServerError().body("Cannot get access to the library-service...");
    }
}
