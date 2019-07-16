package com.gangoffours.pprs.pprs.repositories;

import java.util.Collection;

import com.gangoffours.pprs.pprs.models.DrugInteraction;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
 
@Repository
public interface IDrugInteractionRepository extends CrudRepository<DrugInteraction, Integer> {

    Collection<DrugInteraction> findAll();

    @Query("SELECT i FROM DrugInteraction i WHERE i.drugbankida = :drugBankId")
    Collection<DrugInteraction> findByDrugA(@Param("drugBankId") String drugBankId);

    @Query("SELECT i FROM DrugInteraction i WHERE i.drugbankidb = :drugBankId")
    Collection<DrugInteraction> findByDrugB(@Param("drugBankId") String drugBankId);
}