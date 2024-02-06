package com.harmonycare.domain.checklist.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "checklist_days")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DayEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "day_entity_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "day")
    private Day day;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist_id")
    private Checklist checklist;

    @Builder
    public DayEntity(Long id, Day day) {
        this.id = id;
        this.day = day;
    }
}
