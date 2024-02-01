package com.harmonycare.domain.checklist.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "checklist_days")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DayEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Day day;

    @Builder
    public DayEntity(Long id, Day day) {
        this.id = id;
        this.day = day;
    }
}
