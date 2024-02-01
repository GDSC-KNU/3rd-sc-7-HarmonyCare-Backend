package com.harmonycare.domain.checklist.controller;

import com.harmonycare.domain.checklist.dto.request.CheckListSaveRequest;
import com.harmonycare.domain.checklist.dto.response.CheckListReadResponse;
import com.harmonycare.domain.checklist.service.CheckListService;
import com.harmonycare.global.util.ApiUtil.ApiSuccessResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.harmonycare.global.util.ApiUtil.*;

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
            @PathVariable("checklistId") Long oldCheckListId
        )
    {
        Long newCheckListId = checkListService.updateCheckList(requestBody, oldCheckListId);
        return ResponseEntity.ok().body(success(HttpStatus.OK, newCheckListId));
    }

    @DeleteMapping("/{checklistId}")
    public ResponseEntity<ApiSuccessResult<?>> delete(
       @PathVariable("checklistId") Long deleteId) {
        checkListService.deleteCheckList(deleteId);

        return ResponseEntity.ok().body(success(HttpStatus.OK));
    }
}
