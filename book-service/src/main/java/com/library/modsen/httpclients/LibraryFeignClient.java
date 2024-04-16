package com.library.modsen.httpclients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import test.modsen.libraryservice.core.dto.BookFindDTO;

@FeignClient(value = "${app.api.feign.clients.library.name}")
public interface LibraryFeignClient {
    @PostMapping("/add")
    ResponseEntity<String> sendRequestToCreateRecord(
            @RequestBody BookFindDTO bookFindDTO
    );

}

