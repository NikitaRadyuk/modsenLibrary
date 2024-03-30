package com.library.modsen.httpclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import test.modsen.libraryservice.core.dto.BookRecordDTO;

@FeignClient(name = "records-logs", url = "${custom.feign.record-logs.url}/record")
public interface RecordFeignClient {
    @PostMapping("/add")
    ResponseEntity<String> sendRequestToCreateRecord(
            @RequestBody BookRecordDTO bookRecordDTO
    );

}

