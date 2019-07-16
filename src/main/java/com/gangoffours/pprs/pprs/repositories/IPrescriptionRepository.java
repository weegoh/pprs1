package com.gangoffours.pprs.pprs.repositories;

import java.util.Date;
import java.util.List;

import com.gangoffours.pprs.pprs.models.PatientPrescription;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface IPrescriptionRepository extends CrudRepository<PatientPrescription, Integer> {
    List<PatientPrescription> findBypatientid(Integer patientid);
    List<PatientPrescription> findByDatetimeBetween(Date startDate, Date endDate);
}