package com.gangoffours.pprs.pprs.services;

import java.util.Collection;

import com.gangoffours.pprs.pprs.viewmodels.PatientDetailModel;

public interface IPatientService
{
    PatientDetailModel GetPatientDetail(Integer patientId);
    Collection<PatientDetailModel> FindByName(String patientName);
    void UpdatePatient(PatientDetailModel patientDetail);
    int CreatePatient(PatientDetailModel patientDetail);

}