package com.gangoffours.pprs.pprs.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.gangoffours.pprs.pprs.models.Patient;
import com.gangoffours.pprs.pprs.models.PatientPrescription;
import com.gangoffours.pprs.pprs.repositories.IDrugRepository;
import com.gangoffours.pprs.pprs.repositories.IPatientRepository;
import com.gangoffours.pprs.pprs.repositories.IPrescriptionRepository;
import com.gangoffours.pprs.pprs.viewmodels.XmlAddress;
import com.gangoffours.pprs.pprs.viewmodels.XmlMedicationDispense;
import com.gangoffours.pprs.pprs.viewmodels.XmlMedicationDoseQuantity;
import com.gangoffours.pprs.pprs.viewmodels.XmlMedicationItem;
import com.gangoffours.pprs.pprs.viewmodels.XmlPatient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService implements IReportService {

    private IPrescriptionRepository _prescriptionRepo;
    private IDrugRepository _drugRepo;
    private IPatientRepository _patientRepo;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");
    private SimpleDateFormat htmlSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    // IoC
    @Autowired
    public ReportService (IPrescriptionRepository prescriptionRepo, IDrugRepository drugRepo, IPatientRepository patientRepo) {
        _prescriptionRepo = prescriptionRepo;
        _drugRepo = drugRepo;
        _patientRepo = patientRepo;
    } 

    @Override
    public List<XmlMedicationDispense> FindByDateBetween(String startDate, String endDate) {
       return _prescriptionRepo
            .findByDatetimeBetween(StringToDate(startDate),StringToDate(endDate))
            .stream()
            .map(this::EntityToView)
            .collect(Collectors.toList());
    }

    @Override
    public List<XmlMedicationDispense> FindByPatientId(Integer id) {
       return _prescriptionRepo
            .findBypatientid(id)
            .stream()
            .map(this::EntityToView)
            .collect(Collectors.toList());
    }

    private XmlMedicationDispense EntityToView(PatientPrescription prescription) {
        return new XmlMedicationDispense() {{
            dispenseID = prescription.id; 
            dispensingInstitution = "Test Dental Practice";
            dispenseDateTime = DateToString(prescription.datetime);
            dispensedBy = prescription.user;
            patient = patientEntityToXml(prescription.patientid);
            medicationItem = new XmlMedicationItem(){{
                itemId = prescription.drugbankid;
                frequencyQualifier = prescription.medqualifier;
                dosageInstruction = "Take with food";
                doseQuantity = new XmlMedicationDoseQuantity(){{
                    lowUnit = prescription.dosageunit;
                    lowValue = prescription.dosage;
                }}; 
                dispenseDate = DateToString(prescription.datetime);
                medicationStartDateTime = DateToString(prescription.datetime);
                medicationEndDateTime = DateToString(prescription.datetime);
                reasonForMedication = "Because this person is sick";
                quantityDispensed = prescription.medqty;
                duration = prescription.duration;
                durationUnits = prescription.medunit;
                medicationName = _drugRepo.getNameByDrugbankid(prescription.drugbankid).name;
                frequency = prescription.medfreq;
            }};
        }};
    }

    private XmlPatient patientEntityToXml(Integer patientId){
        Patient patient = _patientRepo.getById(patientId);
        return new XmlPatient() {{
            this.patientId = patient.id;
            patientName = patient.last_name + ", "+ patient.first_name;
            dateOfBirth = patient.date_of_birth;
            address = new XmlAddress(){{
                street_num = patient.street_num;
                unit_num = patient.unit_num;
                street = patient.street;
                suburb = patient.suburb;
                state = patient.state;
                postcode = patient.postcode;
                country = patient.country;
            }};
        }};
    }

    public XmlPatient GetXmlPatientById(Integer patientId){
        return patientEntityToXml(patientId);
    }

    public String DateToString(Date date){
        return sdf.format(date);
    }
    public Date StringToDate(String dateString){
        
        Date date = new Date();
        try {
            date = htmlSdf.parse(dateString);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
