package com.gangoffours.pprs.pprs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gangoffours.pprs.pprs.models.PatientPrescription;
import com.gangoffours.pprs.pprs.models.PrescriptionDefault;
import com.gangoffours.pprs.pprs.viewmodels.PrescriptionModel;
import com.gangoffours.pprs.pprs.repositories.IPrescriptionRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class DispensePrescriptionService implements IDispensePrescriptionService{

    private IPrescriptionRepository _prescriptionRepo;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyy");

    @Autowired
    public DispensePrescriptionService (IPrescriptionRepository prescriptionRepo) {
        _prescriptionRepo = prescriptionRepo;
    } 

    public int SavePrescription(PrescriptionModel prescription) {
        return _prescriptionRepo.save(ToEntity(prescription)).id;
    }

    public PrescriptionModel ToModel(PrescriptionDefault prescription){
        PrescriptionModel script = new PrescriptionModel();

        if (prescription != null){
            script.medQty = prescription.medqty;
            script.medUnit = prescription.medunit;
            script.dosage = prescription.dosage;
            script.dosageUnit = prescription.dosageunit;
            script.medFreq = prescription.medfreq;
            script.duration = prescription.duration;
            script.medQualifier = prescription.medqualifier;
            script.unitPerDosage = prescription.unitperdosage;
        }
        else {
            script.medQty = "30";
            script.medUnit = "capsule";
            script.dosage = "500";
            script.dosageUnit = "mg";
            script.medFreq = "3 times daily";
            script.duration = "5 days";
            script.medQualifier = "When needed";
            script.unitPerDosage = "1";
        }
        return script;
    }

    public PatientPrescription ToEntity(PrescriptionModel prescription){
        return new PatientPrescription(
            prescription.Id,
            prescription.patientId,
            prescription.drugBankId,
            prescription.user,
            StringToDate(prescription.dateTime),
            prescription.medQty,
            prescription.medUnit,
            prescription.dosage,
            prescription.dosageUnit,
            prescription.medFreq,
            prescription.duration,
            prescription.medQualifier,
            prescription.unitPerDosage
        );
    }

    public String DateToString(Date date){
        return sdf.format(date);
    }

    public Date StringToDate(String dateString){
        
        Date date = new Date();
        try {
            date = sdf.parse(dateString);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}