package com.gangoffours.pprs.pprs.models;

import javax.persistence.*;

import lombok.RequiredArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.AccessLevel;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
@Entity(name = "prescriptiondefaults")
public class PrescriptionDefault {

    @Id
    public String drugbankid;

    @OneToOne
    @JoinColumn(name = "drugbankid")
    @MapsId
    public Drug drug;
    
    @NonNull
    @Column(nullable=false)
    public String medqty;

    @NonNull
    @Column(nullable=false)
    public String medunit;

    @NonNull
    @Column(nullable=false)
    public String dosage;

    @NonNull
    @Column(nullable=false)
    public String dosageunit;

    @NonNull
    @Column(nullable=false)
    public String medfreq;

    public String duration;

    public String medqualifier;

    @NonNull
    @Column(nullable=false)
    public String unitperdosage;
}