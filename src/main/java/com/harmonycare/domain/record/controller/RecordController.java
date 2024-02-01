package com.harmonycare.domain.record.controller;

import com.harmonycare.domain.record.dto.request.RecordSaveRequest;
import com.harmonycare.domain.record.dto.response.RecordReadResponse;
import com.harmonycare.domain.record.service.RecordService;
import com.harmonycare.global.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.harmonycare.global.util.ApiUtil.success;

@RestController
@RequestMapping("/record")
@RequiredArgsConstructor
public class RecordController {
    private final RecordService recordService;

    @PostMapping
    public ResponseEntity<ApiUtil.ApiSuccessResult<Long>> save(
            @RequestBody RecordSaveRequest request
    ) {
        Long recordId = recordService.saveRecord(request);

        return ResponseEntity.ok().body(success(HttpStatus.CREATED, recordId));
    }

    @GetMapping("/{recordId}")
    public ResponseEntity<ApiUtil.ApiSuccessResult<RecordReadResponse>> read(
            @PathVariable("recordId") Long id
    ) {
        RecordReadResponse response = recordService.readRecord(id);

        return ResponseEntity.ok().body(ApiUtil.success(HttpStatus.OK, response));
    }

    @PostMapping("/{recordId}")
    public ResponseEntity<ApiUtil.ApiSuccessResult<Long>> update(
            @RequestBody RecordSaveRequest request,
            @PathVariable("recordId") Long id
    ) {
        Long recordId = recordService.updateRecord(request, id);

        return ResponseEntity.ok().body(success(HttpStatus.OK, recordId));
    }

    @DeleteMapping("/{recordId}")
    public ResponseEntity<ApiUtil.ApiSuccessResult<?>> delete(
            @PathVariable("recordId") Long id
    ) {
        recordService.deleteRecord(id);

        return ResponseEntity.ok().body(success(HttpStatus.OK));
    }

}
