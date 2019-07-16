package com.gangoffours.pprs.pprs.models;

import javax.persistence.*;

import lombok.RequiredArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.AccessLevel;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access=AccessLevel.PUBLIC, force=true)
@Entity
public class DrugInteraction {
    @Id
    @Column(nullable=false)
    @NonNull
    private Integer id;
    @Column(name="drugbankida")
    private String drugbankida;
    // @ManyToOne(fetch = FetchType.LAZY,optional=false)
    // @JoinColumn(name = "drugbankida", nullable = false)
    // @NonNull
    // private Drug drugA;
    @Column(name="drugbankidb")
    private String drugbankidb;
    // @ManyToOne(fetch = FetchType.LAZY,optional=false)
    // @JoinColumn(name = "drugbankidb", nullable = false)
    // @NonNull
    // private Drug drugB;
    @NonNull
    @Column(nullable=false)
    private Integer level;
}