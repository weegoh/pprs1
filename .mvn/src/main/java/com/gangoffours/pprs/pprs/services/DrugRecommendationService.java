package com.gangoffours.pprs.pprs.services;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.gangoffours.pprs.pprs.viewmodels.DrugModel;
import com.gangoffours.pprs.pprs.viewmodels.DrugRecommendation;
import com.gangoffours.pprs.pprs.common.validator.StringHelper;
import com.gangoffours.pprs.pprs.models.Drug;
import com.gangoffours.pprs.pprs.models.Patient;
import com.gangoffours.pprs.pprs.repositories.IDrugRepository;
import com.gangoffours.pprs.pprs.repositories.IPatientRepository;

@Service
public class DrugRecommendationService implements IDrugRecommendationService {

    private final IDrugRepository _drugRepository;
    private final IPatientRepository _patientRepository;
    private final IDrugVectorService _drugVectorService;
    private final IDrugInteractivityService _drugInteractivityService;

    @Autowired
    public DrugRecommendationService (
        IDrugRepository drugRepository,
        IPatientRepository patientRepository,
        IDrugVectorService drugVectorService,
        IDrugInteractivityService drugInteractivityService
    ) {
        _drugRepository = drugRepository;
        _patientRepository = patientRepository;
        _drugVectorService = drugVectorService;
        _drugInteractivityService = drugInteractivityService;
    }

    public DrugRecommendation getRecommendation(
        String candidateDrugBankId,
        String similarDrug,
        Double similarity,
        Collection<DrugModel> allergicDrugs,
        Collection<DrugModel> currentDrugs
    ) {
        return new DrugRecommendation(){{
            Drug dA = _drugRepository.findByDrugbankid(similarDrug);
            setDrugBankId(dA.getDrugbankid());
            setDrugName(StringHelper.Capitalise(dA.getName()));
            setSimilarity(similarity);
            setAllergicDrugs(
                allergicDrugs
                    .stream()
                    .filter(dB -> (dA.drugclass.equals(dB.drugclass) && !dA.drugclass.equals("")))
                    .map(d -> Pair.of(
                        d,
                        _drugVectorService.GetSimilarity(
                            candidateDrugBankId,
                            d.id
                        )
                    ))
                    .sorted((a,b) -> b.getSecond().compareTo(a.getSecond()))
                    .collect(Collectors.toList())
            );
            setInteractiveDrugs(
                _drugInteractivityService.GetInteractiveDrugs(
                    dA.getDrugbankid(),
                    currentDrugs
                        .stream()
                        .map(d -> d.id)
                        .collect(Collectors.toSet())
                )
            );
            setCurrentDrugSimilarity(
                currentDrugs
                    .stream()
                    .map(d -> Pair.of(
                        d,
                        _drugVectorService.GetSimilarity(similarDrug, d.id))
                    )
                    .sorted((a,b) -> b.getSecond().compareTo(a.getSecond()))
                    .collect(Collectors.toList())
            );
        }};
    }
    // Returns a list of sorted drug recommendations with the prescribed drug as the first in the list
    public Collection<DrugRecommendation> getRecommendations(
        int patientId, 
        String candidateDrugBankId, 
        double interactionThreshold, 
        double currentDrugThreshold
    ) {
        Patient patient = _patientRepository.findById(patientId).get();

        Collection<DrugModel> allergicDrugs = patient.getAllergicdrugs()
            .stream()
            .map(this::convertDrugModel)
            .collect(Collectors.toList());
        Collection<DrugModel> currentDrugs = patient.getCurrentdrugs()
            .stream()
            .map(this::convertDrugModel)
            .collect(Collectors.toList());

        Collection<DrugRecommendation> recommendations = _drugVectorService
            .GetSimilarDrugs(candidateDrugBankId)
            .filter(f ->
                !f.getFirst().equals(candidateDrugBankId) &&
                f.getSecond() > (interactionThreshold/100)
            )
            .map(d -> getRecommendation(
                candidateDrugBankId,
                d.getFirst(),
                d.getSecond(),
                allergicDrugs,
                currentDrugs
            ))
            .filter(r -> r.getAverageCurrentSimilarity() > (currentDrugThreshold/100))
            .sorted()
            .collect(Collectors.toList());

        return recommendations;
    }

    private DrugModel convertDrugModel(Drug drug) {
        return new DrugModel(
            drug.getDrugbankid(),
            drug.getName(),
            drug.getDrugclass()
        );
    }
}