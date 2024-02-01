package com.harmonycare.domain.checklist.entity;

import com.harmonycare.domain.checklist.dto.request.ChecklistUpdateRequest;
import com.harmonycare.domain.member.entity.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Checklist {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "checklist_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Member.class)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "title")
    private String title;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "checklist_id")
    private List<DayEntity> dayList = new ArrayList<>();

    @Column(name = "check_time")
    private LocalDateTime checkTime;

    @Column(name = "is_check")
    private Boolean isCheck;

    @Builder
    public Checklist(Member member, String title, List<DayEntity> dayList, LocalDateTime checkTime, Boolean isCheck) {
        this.member = member;
        this.title = title;
        this.dayList = dayList;
        this.checkTime = checkTime;
        this.isCheck = isCheck;
    }

    public void update(ChecklistUpdateRequest request) {
        if (request.title() != null) this.title = request.title();
        if (request.days() != null && !request.days().isEmpty()) {
            this.dayList = Day.getDayEntity(request.days());
        }
        // TODO: 2/1/24 CheckTime Update 구현
    }

    public void check() {
        this.isCheck = !this.isCheck;
    }
}
