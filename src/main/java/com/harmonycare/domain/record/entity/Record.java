package com.harmonycare.domain.record.entity;

import com.harmonycare.domain.baby.entity.Baby;
import com.harmonycare.domain.record.dto.request.RecordUpdateRequest;
import com.harmonycare.global.util.DateTimeUtil;
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

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "record")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Record {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "baby_id")
    private Baby baby;

    @Column(name = "record_task")
    @Enumerated(EnumType.STRING)
    private RecordTask recordTask;

    @Column(name = "record_time")
    private LocalDateTime startTime;

    @Column(name = "minute")
    private LocalDateTime endTime;

    @Column(name = "description")
    private String description;

    @Builder
    public Record(Long id, Baby baby, RecordTask recordTask, LocalDateTime startTime, LocalDateTime endTime, String description) {
        this.id = id;
        this.baby = baby;
        this.recordTask = recordTask;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }

    public void update(RecordUpdateRequest request) {
        if (request.recordTask() != null) {
            this.recordTask = request.recordTask();
        }

        if (request.startTime() != null) {
            this.startTime = DateTimeUtil.stringToLocalDateTime(request.startTime());
        }

        if (request.endTime() != null) {
            this.endTime = DateTimeUtil.stringToLocalDateTime(request.endTime());
        }

        if (request.description() != null) {
            this.description = request.description();
        }
    }
}
