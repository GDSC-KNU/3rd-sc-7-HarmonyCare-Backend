package com.harmonycare.domain.checklist.entity;

import java.util.List;
import java.util.stream.Collectors;

public enum Day {
    MONDAY, TUESDAY, WEDNEDSDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

    public static List<DayEntity> dayListToDayEntityList(List<Day> dayList) {
        return dayList.stream()
                .map(day -> DayEntity.builder().day(day).build())
                .collect(Collectors.toList());
    }


    public static List<Day> dayEntityListToDayList(List<DayEntity> dayEntityList) {
        return dayEntityList.stream()
                .map(DayEntity::getDay)
                .collect(Collectors.toList());
    }
}
