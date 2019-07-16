package com.gangoffours.pprs.pprs.repositories;

import java.util.Collection;

import com.gangoffours.pprs.pprs.models.DrugScalar;
import com.gangoffours.pprs.pprs.models.DrugScalarKey;

import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Scope(value = "request")
@Repository
public interface IDrugScalarRepository extends CrudRepository<DrugScalar, DrugScalarKey> {
    Collection<DrugScalar> findAll();
    Collection<DrugScalar> findById_DrugBankId(String drugBankId);
}