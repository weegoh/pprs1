package com.gangoffours.pprs.pprs.controllers;

import com.gangoffours.pprs.pprs.common.AuthControllerBase;
import com.gangoffours.pprs.pprs.models.Drug;
import com.gangoffours.pprs.pprs.repositories.IDrugRepository;
import com.gangoffours.pprs.pprs.repositories.IUserRepository;
import com.gangoffours.pprs.pprs.services.IDrugRecommendationService;
import com.gangoffours.pprs.pprs.services.IPatientDrugService;
import com.gangoffours.pprs.pprs.services.IPatientService;
import com.gangoffours.pprs.pprs.viewmodels.DrugModel;
import com.gangoffours.pprs.pprs.viewmodels.DrugRecommendationViewModel;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DrugRecommendationController extends AuthControllerBase {

    private IPatientService _patientService;
    private IPatientDrugService _patientDrugService;
    private IDrugRecommendationService _drugRecommendationService;
    private IDrugRepository _drugRepository;

    private static final Double _candidateDstDefault = 90.0;
    private static final Double _currentDstDefault = 60.0;

    public DrugRecommendationController(IUserRepository userRepository, 
        IPatientService patientService, 
        IPatientDrugService patientDrugService,
        IDrugRecommendationService drugRecommendationService,
        IDrugRepository drugRepository) {
        super(userRepository);
        _patientService = patientService;
        _patientDrugService = patientDrugService;
        _drugRecommendationService = drugRecommendationService;
        _drugRepository = drugRepository;
    }

    @ModelAttribute("PageTitle")
    public String getPageTitle() {
        return "Prescription Recommendations";
    }

    @GetMapping("/patient/{patientid}/addPrescribingDrug/{candidateDrugId}")   
    public String getRecommendation(
        Model model,
        @PathVariable("patientid")
        Integer patientId,
        @PathVariable("candidateDrugId")
        String candidateDrugId,
        @RequestParam(name = "candidateDst", required = false)
        Double candidateDst,
        @RequestParam(name = "currentDst", required = false)
        Double currentDst
    ) {
        model.addAttribute("model", new DrugRecommendationViewModel() {{
            candidateDrugSimilarityThreshold = candidateDst != null ? candidateDst : _candidateDstDefault;
            currentDrugSimilarityThreshold = currentDst != null ? currentDst : _currentDstDefault;
            patient = _patientService.GetPatientDetail(patientId);
            candidateDrug = new DrugModel() {{
                Drug m = _drugRepository.findByDrugbankid(candidateDrugId);
                setId(m.getDrugbankid());
                setName(m.getName());
                setDrugclass(m.getDrugclass());
            }};
            currentDrugs = _patientDrugService.getCurrent(patientId);
            allergicDrugs = _patientDrugService.getAllergic(patientId);
            candidateRecommendation = _drugRecommendationService.getRecommendation(
                candidateDrugId,
                candidateDrugId,
                1.0,
                allergicDrugs,
                currentDrugs
            );
            drugRecommendations = _drugRecommendationService.getRecommendations(
                patientId,
                candidateDrugId,
                candidateDrugSimilarityThreshold,
                currentDrugSimilarityThreshold
            );
        }});

        return "recommendations/index";
    }
}