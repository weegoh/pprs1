package com.gangoffours.pprs.pprs.services;

import java.util.Collection;
import com.gangoffours.pprs.pprs.viewmodels.DrugModel;

public interface IPatientDrugService
{
    Collection<DrugModel> getCurrent(int patientid);
    Collection<DrugModel> getAllergic(int patientid);
    void addCurrent(int patientid, String drugid);
    void addAllergic(int patientid, String drugid);
    void removeCurrent(int patientid, String drugid);
    void removeAllergic(int patientid, String drugid);
}