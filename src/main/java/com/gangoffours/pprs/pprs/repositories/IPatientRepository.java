package com.gangoffours.pprs.pprs.repositories;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

import com.gangoffours.pprs.pprs.models.Drug;
import com.gangoffours.pprs.pprs.models.Patient;

@Scope(value = "request")
@Repository
public interface IPatientRepository extends CrudRepository<Patient, Integer>{
    @Query("SELECT p from Patient p where p.first_name LIKE %:value% OR p.last_name LIKE %:value%")
    Collection<Patient> findByName(@Param("value") String value);

    Optional<Drug> findByCurrentdrugs_drugbankid(String drugBankId);
    Optional<Drug> findByAllergicdrugs_drugbankid(String drugBankId);

    Patient getById(Integer id);
}