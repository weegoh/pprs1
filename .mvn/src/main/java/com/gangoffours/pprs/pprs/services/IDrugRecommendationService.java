package com.gangoffours.pprs.pprs.services;

import java.util.Collection;

import com.gangoffours.pprs.pprs.viewmodels.DrugModel;
import com.gangoffours.pprs.pprs.viewmodels.DrugRecommendation;

public interface IDrugRecommendationService
{
    DrugRecommendation getRecommendation(
        String candidateDrugBankId,
        String similarDrug,
        Double similarity,
        Collection<DrugModel> allergicDrugs,
        Collection<DrugModel> currentDrugs
    );

    Collection<DrugRecommendation> getRecommendations(
        int patientId,
        String candidateDrugBankId,
        double prescribedThresh,
        double currentDrugThresh
    );
}