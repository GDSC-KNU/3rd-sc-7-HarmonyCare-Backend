package com.harmonycare.domain.checklist.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckList {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "checklist_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "days")
    private Day days;

    @Column(name = "check_time")
    private LocalDateTime checkTime;

    @Column(name = "is_check")
    private Boolean isCheck;

    @Builder
    public CheckList(Long id, String title, Day days, LocalDateTime checkTime, Boolean isCheck) {
        this.id = id;
        this.title = title;
        this.days = days;
        this.checkTime = checkTime;
        this.isCheck = isCheck;
    }
}
