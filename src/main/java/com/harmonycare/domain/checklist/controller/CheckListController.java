package com.harmonycare.domain.checklist.controller;

import com.harmonycare.domain.checklist.dto.request.CheckListSaveRequest;
import com.harmonycare.domain.checklist.dto.response.CheckListReadResponse;
import com.harmonycare.domain.checklist.service.CheckListService;
import com.harmonycare.global.util.ApiUtil.ApiSuccessResult;
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
@RequestMapping("/checklist")
@RequiredArgsConstructor
public class CheckListController {

    private final CheckListService checkListService;

    @PostMapping
    public ResponseEntity<ApiSuccessResult<Long>> save(
            @RequestBody CheckListSaveRequest requestBody)
    {
        Long checkListID = checkListService.saveCheckList(requestBody);
        return ResponseEntity.ok().body(success(HttpStatus.CREATED, checkListID));
    }

    @GetMapping("/{checklistId}")
    public ResponseEntity<ApiSuccessResult<CheckListReadResponse>> read(
            @PathVariable("checklistId") Long id)
    {
        CheckListReadResponse checkListReadResponse = checkListService.readCheckList(id);
        return ResponseEntity.ok().body(success(HttpStatus.OK, checkListReadResponse));
    }

    @PostMapping("/{checklistId}")
    public ResponseEntity<ApiSuccessResult<Long>> update(
            @RequestBody CheckListSaveRequest requestBody,
            @PathVariable("checklistId") Long id
        )
    {
        Long newCheckListId = checkListService.updateCheckList(requestBody, id);
        return ResponseEntity.ok().body(success(HttpStatus.OK, newCheckListId));
    }

    @DeleteMapping("/{checklistId}")
    public ResponseEntity<ApiSuccessResult<?>> delete(
       @PathVariable("checklistId") Long deleteId) {
        checkListService.deleteCheckList(deleteId);

        return ResponseEntity.ok().body(success(HttpStatus.OK));
    }
}
