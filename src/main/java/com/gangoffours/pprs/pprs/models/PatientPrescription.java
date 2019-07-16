package com.gangoffours.pprs.pprs.models;

import javax.persistence.*;
import java.util.Date;

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
@Entity(name = "patientprescription")
public class PatientPrescription {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer id;

    @NonNull
    @Column(nullable=false)
    public Integer patientid;
    
    @NonNull
    @Column(nullable=false)
    public String drugbankid;

    @NonNull
    @Column(nullable=false)
    public String user;

    @NonNull
    @Column(nullable=false)
    public Date datetime;
    
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

    public String unitperdosage;
}