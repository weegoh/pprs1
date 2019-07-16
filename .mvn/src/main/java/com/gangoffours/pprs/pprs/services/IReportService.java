package com.gangoffours.pprs.pprs.services;

import java.util.List;

import com.gangoffours.pprs.pprs.viewmodels.XmlMedicationDispense;
import com.gangoffours.pprs.pprs.viewmodels.XmlPatient;

public interface IReportService
{
    List<XmlMedicationDispense> FindByDateBetween(String startDate, String endDate);
    List<XmlMedicationDispense> FindByPatientId(Integer id);
    XmlPatient GetXmlPatientById(Integer id);
}