package com.gangoffours.pprs.pprs.viewmodels;

import java.util.Collection;

public class DrugRecommendationViewModel {
    public Double candidateDrugSimilarityThreshold;
    public Double currentDrugSimilarityThreshold;
    public PatientDetailModel patient;
    public DrugModel candidateDrug;
    public Collection<DrugModel> currentDrugs;
    public Collection<DrugModel> allergicDrugs;
    public DrugRecommendation candidateRecommendation;
    public Collection<DrugRecommendation> drugRecommendations;
}