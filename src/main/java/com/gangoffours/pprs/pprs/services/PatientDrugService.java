package com.gangoffours.pprs.pprs.services;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gangoffours.pprs.pprs.viewmodels.*;
import com.gangoffours.pprs.pprs.models.*;
import com.gangoffours.pprs.pprs.repositories.*;
import com.gangoffours.pprs.pprs.common.ResourceNotFoundException;

@Service
@Transactional
public class PatientDrugService implements IPatientDrugService {

    private IDrugRepository _drugRepo;
    private IPatientRepository _patientRepo;

    @Autowired
    public PatientDrugService(IDrugRepository drugRepo, IPatientRepository patientRepo) {
        _drugRepo = drugRepo;
        _patientRepo = patientRepo;
    }

    // Return the list of current drug objects from the repository based on a
    // patient ID
    public Collection<DrugModel> getCurrent(int patientid) {
        Optional<Patient> patient = _patientRepo.findById(patientid);
        if (!patient.isPresent()) throw new ResourceNotFoundException();

        return patient
            .get()
            .getCurrentdrugs()
            .stream()
            .map(d -> new DrugModel(
                d.getDrugbankid(),
                d.getName(),
                d.getDrugclass()
            ))
            .collect(Collectors.toList());
    }

    // Return the list of allergic drug objects from the repository based on a
    // patient ID
    public Collection<DrugModel> getAllergic(int patientid) {
        Optional<Patient> patient = _patientRepo.findById(patientid);
        if (!patient.isPresent()) throw new ResourceNotFoundException();

        return patient
            .get()
            .getAllergicdrugs()
            .stream()
            .map(d -> new DrugModel(
                d.getDrugbankid(),
                d.getName(),
                d.getDrugclass()
            ))
            .collect(Collectors.toList());
    }

    // Add a drug to the patient's current drugs in the repository using the drug
    // bank id and patient id
    @Transactional
    public void addCurrent(int patientid, String drugid) {
        Optional<Patient> patientOptional = _patientRepo.findById(patientid);
        Drug drug = _drugRepo.findByDrugbankid(drugid);
        if (!patientOptional.isPresent() || drug == null) return;

        Patient patient = patientOptional.get();
        if (patient.getCurrentdrugs().stream().anyMatch(d -> drugid.equals(d.getDrugbankid()))) return;

        patient.currentdrugs.add(drug);
        _patientRepo.save(patient);
    }

    // Add a drug to the patient's allergic drugs in the repository
    public void addAllergic(int patientid, String drugid) {
        Optional<Patient> patientOptional = _patientRepo.findById(patientid);
        Drug drug = _drugRepo.findByDrugbankid(drugid);
        if (!patientOptional.isPresent() || drug == null) return;

        Patient patient = patientOptional.get();
        if (patient.getAllergicdrugs().stream().anyMatch(d -> drugid.equals(d.getDrugbankid()))) return;
        
        patient.allergicdrugs.add(drug);
        _patientRepo.save(patient);
    }

    // Remove a drug from the patient's current drugs in the repository using the
    // drug bank id and patient id
    public void removeCurrent(int patientid, String drugid) {
        Patient patient = _patientRepo.findById(patientid).get();

        Optional<Drug> drug = patient.getCurrentdrugs()
            .stream()
            .filter(d -> d.getDrugbankid().equals(drugid))
            .findFirst();

        if (!drug.isPresent()) return;

        patient.currentdrugs.remove(drug.get());
        _patientRepo.save(patient);
    }

    // Remove a drug from the patient's allergic drugs in the repository
    public void removeAllergic(int patientid, String drugid) {
        Patient patient = _patientRepo.findById(patientid).get();

        Optional<Drug> drug = patient.getAllergicdrugs()
            .stream()
            .filter(d -> d.getDrugbankid().equals(drugid))
            .findFirst();

        if (!drug.isPresent()) return;

        patient.allergicdrugs.remove(drug.get());
        _patientRepo.save(patient);
    }
}