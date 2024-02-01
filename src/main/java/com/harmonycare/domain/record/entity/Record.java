package com.harmonycare.domain.record.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Column(name = "record_task")
    @Enumerated(EnumType.STRING)
    private RecordTask recordTask;

    @Column(name = "record_time")
    private LocalDateTime recordTime;

    @Column(name = "amount")
    private int amount;

    @Builder
    public Record(Long id, RecordTask recordTask, LocalDateTime recordTime, int amount) {
        this.id = id;
        this.recordTask = recordTask;
        this.recordTime = recordTime;
        this.amount = amount;
    }
}
