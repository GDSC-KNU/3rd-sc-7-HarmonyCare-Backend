package com.harmonycare.domain.checklist.entity;

import java.util.List;
import java.util.stream.Collectors;

public enum Day {
    MONDAY, TUESDAY, WEDNEDSDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

    public static List<DayEntity> getDayEntity(List<Day> dayList) {
        return dayList.stream()
                .map(day -> DayEntity.builder().day(day).build())
                .collect(Collectors.toList());
    }
}
