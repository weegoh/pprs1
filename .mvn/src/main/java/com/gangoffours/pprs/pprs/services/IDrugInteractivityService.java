package com.gangoffours.pprs.pprs.services;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.Future;

import com.gangoffours.pprs.pprs.common.Triplet;
import com.gangoffours.pprs.pprs.viewmodels.DrugInteractionType;
import com.gangoffours.pprs.pprs.viewmodels.DrugModel;

public interface IDrugInteractivityService {
    Collection<Triplet<DrugModel, DrugInteractionType, Double>> GetInteractiveDrugs(
        String drugBankId,
        Set<String> currentDrugBankIds
    );

    Future<Collection<Triplet<String,String,Integer>>> GetInteractionsAsync();
}