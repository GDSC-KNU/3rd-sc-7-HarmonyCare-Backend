package com.harmonycare.domain.baby.entity;

import com.harmonycare.domain.member.entity.Member;
import com.harmonycare.domain.record.entity.Record;
import jakarta.persistence.CascadeType;
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
@Table(name = "baby")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Baby {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "baby_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "birthdate")
    private LocalDateTime birthdate;

    @Column(name = "birth_weight")
    private Float birthWeight;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Member.class)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "baby", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Record> recordList = new ArrayList<>();

    @Builder
    public Baby(String name, Gender gender, LocalDateTime birthdate, Float birthWeight, Member member) {
        this.name = name;
        this.gender = gender;
        this.birthdate = birthdate;
        this.birthWeight = birthWeight;
        this.member = member;
    }

}
