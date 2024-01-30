package com.harmonycare.domain.checklist.controller;

import com.harmonycare.domain.checklist.dto.request.CheckListSaveRequest;
import com.harmonycare.domain.checklist.dto.response.CheckListReadResponse;
import com.harmonycare.domain.checklist.service.CheckListService;
import com.harmonycare.global.util.ApiUtil;
import com.harmonycare.global.util.ApiUtil.ApiSuccessResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checklist")
@RequiredArgsConstructor
public class CheckListController {

    private final CheckListService checkListService;

    @PostMapping
    public ResponseEntity<ApiSuccessResult<Long>> save(
            @RequestBody CheckListSaveRequest requestBody)
    {
        Long checkListID = checkListService.saveCheckList(requestBody);
        return ResponseEntity.ok().body(ApiUtil.success(HttpStatus.OK, checkListID));
    }

    @GetMapping("/{checklistId}")
    public ResponseEntity<ApiSuccessResult<CheckListReadResponse>> read(
            @PathVariable("checklistId") Long id) {

        return null;

    }
}
