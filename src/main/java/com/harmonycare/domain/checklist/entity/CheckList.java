package com.harmonycare.domain.checklist.entity;

import java.util.*;
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

   /*
    @ElementCollection
    @CollectionTable(name = "checklist_days", joinColumns =
        @JoinColumn(name = "checklist_id")
    )
    @Column(name = "day")
    @Enumerated(EnumType.STRING)
    private List<Day> days = new ArrayList<>();
    -> 쓰지말고 일대다 연관관계로 풀어서 사용하자 +
            Day를 DayEntity로 한번 래핑헤서 사용 -> 나중에 db에서 값을 바꿨을때 추적하기 쉬움
    */

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "checklist_id")
    private List<DayEntity> dayList = new ArrayList<>();

    @Column(name = "check_time")
    private LocalDateTime checkTime;

    @Column(name = "is_check")
    private Boolean isCheck;

    @Builder
    public CheckList(Long id, String title, List<DayEntity> dayList, LocalDateTime checkTime, Boolean isCheck) {
        this.id = id;
        this.title = title;
        this.dayList = dayList;
        this.checkTime = checkTime;
        this.isCheck = isCheck;
    }
}
