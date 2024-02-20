package com.harmonycare.domain.checklist.entity;

import com.harmonycare.domain.checklist.dto.request.ChecklistUpdateRequest;
import com.harmonycare.domain.member.entity.Member;
import com.harmonycare.global.util.DateTimeUtil;
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
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "checklist")
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

    @OneToMany(mappedBy = "checklist", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.EAGER)
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
        if (request.title() != null)
            this.title = request.title();

        if (request.days() != null) {
            this.dayList.clear(); // 기존 목록을 지웁니다.
            this.dayList.addAll(Day.dayListToDayEntityList(request.days()));
            this.dayList.forEach(dayEntity -> dayEntity.setChecklist(this));
        }

        if (request.checkTime() != null)
            this.checkTime = DateTimeUtil.stringToLocalDateTime(request.checkTime());

    }

    public void check() {
        if (this.isCheck) this.isCheck = Boolean.FALSE;
        else this.isCheck = Boolean.TRUE;
    }

    /**
     * 연관관계 편의 메서드
     * @param dayList
     */
    public void setDayList(List<DayEntity> dayList) {
        this.dayList = dayList;
        dayList.stream()
                .forEach(dayEntity -> dayEntity.setChecklist(this));
    }
}
