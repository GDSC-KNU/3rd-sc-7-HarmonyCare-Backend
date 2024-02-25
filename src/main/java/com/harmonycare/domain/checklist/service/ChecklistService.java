package com.harmonycare.domain.checklist.service;

import com.harmonycare.domain.checklist.dto.request.ChecklistSaveRequest;
import com.harmonycare.domain.checklist.dto.request.ChecklistUpdateRequest;
import com.harmonycare.domain.checklist.dto.response.ChecklistReadResponse;
import com.harmonycare.domain.checklist.entity.Checklist;
import com.harmonycare.domain.checklist.entity.Day;
import com.harmonycare.domain.checklist.entity.DayEntity;
import com.harmonycare.domain.checklist.exception.ChecklistErrorCode;
import com.harmonycare.domain.checklist.repository.ChecklistRepository;
import com.harmonycare.domain.member.entity.Member;
import com.harmonycare.gemini.dto.request.GeminiRequestDTO;
import com.harmonycare.gemini.service.TextTemplateService;
import com.harmonycare.global.exception.GlobalException;
import com.harmonycare.global.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChecklistService {
    private final ChecklistRepository checkListRepository;
    private final TextTemplateService textTemplateService;

    /**
     * CREATE
     */
    @Transactional
    public Long saveCheckList(ChecklistSaveRequest requestBody, Member member) {
        LocalDateTime resultDateTime = DateTimeUtil.stringToLocalDateTime(requestBody.checkTime());

        Checklist checklist = Checklist.builder()
                .title(requestBody.title())
                .checkTime(resultDateTime)
                .isCheck(false)
                .member(member)
                .build();

        List<DayEntity> dayEntityList = Day.dayListToDayEntityList(requestBody.days(), checklist);
        checklist.setDayList(dayEntityList);
        checkListRepository.save(checklist);

        return checklist.getId();
    }

    /**
     * READ
     */
    public ChecklistReadResponse readCheckList(Long checklistId) {
        Checklist checklist = checkListRepository.findById(checklistId)
                .orElseThrow(() -> new GlobalException(ChecklistErrorCode.CHECKLIST_NOT_FOUND));

        return ChecklistReadResponse.from(checklist);
    }

    /**
     * UPDATE
     */
    @Transactional
    public Long updateCheckList(ChecklistUpdateRequest requestBody, Long oldCheckListId) {
        Checklist checklist = checkListRepository.findById(oldCheckListId)
                .orElseThrow(() -> new GlobalException(ChecklistErrorCode.CHECKLIST_NOT_FOUND));

        checklist.update(requestBody);
        checkListRepository.save(checklist);

        return checklist.getId();
    }

    /**
     * DELETE
     */
    @Transactional
    public void deleteCheckList(Long deleteCheckId) {
        Checklist deleteChecklist = checkListRepository.findById(deleteCheckId)
                .orElseThrow(() -> new GlobalException(ChecklistErrorCode.CHECKLIST_NOT_FOUND));

        checkListRepository.delete(deleteChecklist);
    }

    public List<ChecklistReadResponse> readMyTodayChecklist(LocalDate today, Member member) {
        List<Checklist> checklistList = checkListRepository.findByMemberAndCheckTimeBetween(member,
                today.atStartOfDay(), today.atTime(23, 59, 59));

        return checklistList.stream()
                .map(ChecklistReadResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public Boolean checkChecklist(Long checklistId) {
        Checklist checklist = checkListRepository.findById(checklistId)
                .orElseThrow(() -> new GlobalException(ChecklistErrorCode.CHECKLIST_NOT_FOUND));

        checklist.check();
        checkListRepository.save(checklist);

        return checklist.getIsCheck();
    }

    public String provideTips(LocalDate today, Member member) {

        LocalDate yesterday = today.minusDays(1L);
        List<Checklist> yesterdayChecklists =
                checkListRepository.findByMemberAndCheckTimeBetween(member, yesterday.atStartOfDay(),
                        yesterday.atTime(23, 59, 59));

        String sleepPrompt = "I slept little last night because I was raising a baby. " +
                "Please write a simple tip about this in 60 characters or less and one or two sentences.";

        String exercisePrompt = "I couldn't meet my exercise goal yesterday because of childcare. " +
                "Please tell us today’s simple tip about this in 60 characters or less and one or two sentences.";

        String parentingPrompt = "Give me a quick tip on parenting for a novice parent raising a newborn baby in one or two sentences up to 60 characters";

        return yesterdayChecklists.stream()
                .filter(checklist -> !checklist.getIsCheck())
                .filter(checklist -> checklist.getTitle().equals("Sleep") || checklist.getTitle().equals("Exercise"))
                .findFirst()
                .map(checklist -> {
                    if (checklist.getTitle().equals("Sleep")) {
                        return textTemplateService.provideTip(new GeminiRequestDTO(sleepPrompt));
                    } else if (checklist.getTitle().equals("Exercise")) {
                        return textTemplateService.provideTip(new GeminiRequestDTO(exercisePrompt));
                    }
                    return "";
                })
                .orElse(textTemplateService.provideTip(new GeminiRequestDTO(parentingPrompt)));
    }

    @Transactional
    public void saveDefaultCheckList(LocalDate today, Member member) {
        List<Checklist> checklistListSleep = checkListRepository.findAllByMemberAndTitle(member, "Sleep");
        List<Checklist> checklistListExercise = checkListRepository.findAllByMemberAndTitle(member, "Exercise");

        List<Day> dayList = new ArrayList<>();
        dayList.add(Day.valueOf(String.valueOf(today.getDayOfWeek())));

        if (notExistsByMemberAndTitleAndCreatedDate(checklistListSleep, today)) {
            Checklist checklist = Checklist.builder()
                    .title("Sleep")
                    .checkTime(LocalDateTime.now())
                    .isCheck(false)
                    .member(member)
                    .build();

            List<DayEntity> dayEntityList = Day.dayListToDayEntityList(dayList, checklist);
            checklist.setDayList(dayEntityList);
            checkListRepository.save(checklist);
        }

        if (notExistsByMemberAndTitleAndCreatedDate(checklistListExercise, today)) {
            Checklist checklist = Checklist.builder()
                    .title("Exercise")
                    .checkTime(LocalDateTime.now())
                    .isCheck(false)
                    .member(member)
                    .build();

            List<DayEntity> dayEntityList = Day.dayListToDayEntityList(dayList, checklist);
            checklist.setDayList(dayEntityList);
            checkListRepository.save(checklist);
        }
    }

    private boolean notExistsByMemberAndTitleAndCreatedDate(List<Checklist> checklistList, LocalDate now) {
        for (Checklist checklist : checklistList) {
            if (checklist.getCreatedDate().toLocalDate().equals(now)) {
                return false;
            }
        }

        return true;
    }
}