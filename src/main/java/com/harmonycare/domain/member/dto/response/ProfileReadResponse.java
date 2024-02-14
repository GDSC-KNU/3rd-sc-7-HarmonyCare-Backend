package com.harmonycare.domain.member.dto.response;

import com.harmonycare.domain.baby.entity.Baby;
import com.harmonycare.global.util.DateTimeUtil;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record ProfileReadResponse(String parentName, String email,
                                  List<String> babyNames, List<String> babyBirthDates) {

    public static List<String> getBabyBirthDates(List<Baby> babyList) {
        List<String> babyBirthDates = babyList.stream()
                .map(baby -> DateTimeUtil.localDateTimeToString(baby.getBirthdate()))
                .collect(Collectors.toList());
        return babyBirthDates;
    }

    public static List<String> getBabyNames(List<Baby> babyList) {
        List<String> babyNames = babyList.stream()
                .map(baby -> baby.getName())
                .collect(Collectors.toList());
        return babyNames;
    }
}
