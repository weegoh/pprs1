package com.gangoffours.pprs.pprs.services;

import com.gangoffours.pprs.pprs.viewmodels.PrescriptionModel;
import com.gangoffours.pprs.pprs.models.PrescriptionDefault;
import com.gangoffours.pprs.pprs.models.PatientPrescription;
import java.util.Date;

public interface IDispensePrescriptionService
{
    int SavePrescription(PrescriptionModel prescription);
    PrescriptionModel ToModel(PrescriptionDefault prescription);
    PatientPrescription ToEntity(PrescriptionModel prescription);
    String DateToString(Date date);
    Date StringToDate(String dateString);
}