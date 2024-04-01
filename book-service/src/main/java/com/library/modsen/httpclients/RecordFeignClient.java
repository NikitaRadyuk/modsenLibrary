package com.library.modsen.httpclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import test.modsen.libraryservice.core.dto.BookFindDTO;

@FeignClient("library-service")
public interface RecordFeignClient {
    @PostMapping("/newRecord")
    ResponseEntity<String> sendRequestToCreateRecord(
            @RequestBody BookFindDTO bookFindDTO
    );

}

