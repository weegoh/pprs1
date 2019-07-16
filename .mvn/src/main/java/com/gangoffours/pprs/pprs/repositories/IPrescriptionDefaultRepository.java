package com.gangoffours.pprs.pprs.repositories;

import com.gangoffours.pprs.pprs.models.PrescriptionDefault;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface IPrescriptionDefaultRepository extends CrudRepository<PrescriptionDefault, String> {
    PrescriptionDefault findBydrugbankid(String drugbankid);

}