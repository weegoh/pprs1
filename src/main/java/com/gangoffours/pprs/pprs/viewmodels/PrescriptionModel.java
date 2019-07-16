package com.gangoffours.pprs.pprs.viewmodels;

import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionModel{
    public Integer Id;
    public Integer patientId;
    public String drugBankId;
    public String user;
    public String dateTime;
    public String medQty;
    public String medUnit;
    public String dosage;
    public String dosageUnit;
    public String medFreq;
    public String duration;
    public String medQualifier;
    public String unitPerDosage;
}