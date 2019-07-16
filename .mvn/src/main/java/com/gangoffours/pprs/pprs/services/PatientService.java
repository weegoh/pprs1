package com.gangoffours.pprs.pprs.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import com.gangoffours.pprs.pprs.models.Drug;
import com.gangoffours.pprs.pprs.models.Patient;
import com.gangoffours.pprs.pprs.repositories.IPatientRepository;
import com.gangoffours.pprs.pprs.viewmodels.PatientDetailModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService implements IPatientService {

    private IPatientRepository _patientRepository;

    // IoC
    @Autowired
    public PatientService(IPatientRepository patientRepo) {
        _patientRepository = patientRepo;
    }

    // Get a patient by ID
    @Override
    public PatientDetailModel GetPatientDetail(Integer patientId) {
        Optional<Patient> patient = _patientRepository.findById(patientId);

        return patient.isPresent()
            ? EntityToView(patient.get())
            : null;
    }

    // Get all of the patients from the database and filter to those who contain the
    // search string (patient name)
    @Override
    public Collection<PatientDetailModel> FindByName(String patientName) {
        return _patientRepository
            .findByName(patientName)
            .stream()
            .map(this::EntityToView)
            .collect(Collectors.toList());
    }

    public void UpdatePatient(PatientDetailModel patientDetail) {
        Patient patient = _patientRepository.findById(patientDetail.Id).get();

        patient.first_name = patientDetail.FirstName;
        patient.last_name = patientDetail.LastName;
        patient.date_of_birth = patientDetail.DateOfBirth;
        patient.gender = patientDetail.Gender;
        patient.contact = patientDetail.Contact;
        patient.street_num = patientDetail.StreetNum;
        patient.unit_num = patientDetail.UnitNum;
        patient.street = patientDetail.Street;
        patient.suburb = patientDetail.Suburb;
        patient.state = patientDetail.State;
        patient.postcode = patientDetail.Postcode;
        patient.country = patientDetail.Country;

        _patientRepository.save(patient);
    }

    //Return a patient entity
    private Patient toPatient(PatientDetailModel patientDetail) {
        return new Patient(
            patientDetail.Id, 
            patientDetail.FirstName,
            patientDetail.LastName, 
            patientDetail.DateOfBirth,
            patientDetail.Gender,
            patientDetail.Contact,
            patientDetail.StreetNum, 
            patientDetail.UnitNum, 
            patientDetail.Street,
            patientDetail.Suburb, 
            patientDetail.State,
            patientDetail.Postcode,
            patientDetail.Country,
            patientDetail.CurrentDrugs,
            patientDetail.AllergicDrugs
        );
    }

    private PatientDetailModel EntityToView(Patient patient) {
        return new PatientDetailModel() {{
            Id = patient.id; 
            FirstName = patient.first_name;
            LastName = patient.last_name;
            DateOfBirth = patient.date_of_birth;
            Gender = patient.gender;
            Contact = patient.contact;
            StreetNum = patient.street_num;
            UnitNum = patient.unit_num;
            Street = patient.street;
            Suburb = patient.suburb;
            State = patient.state;
            Postcode = patient.postcode;
            Country = patient.country;
            AllergicDrugs = new ArrayList<Drug>();
            CurrentDrugs = new ArrayList<Drug>();
        }};
    }

    public int CreatePatient(PatientDetailModel patientDetail)
    {
        return _patientRepository.save(toPatient(patientDetail)).id;
    }
}