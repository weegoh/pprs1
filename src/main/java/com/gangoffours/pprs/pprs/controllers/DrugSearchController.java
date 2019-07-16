package com.gangoffours.pprs.pprs.controllers;

import com.gangoffours.pprs.pprs.services.*;
import com.gangoffours.pprs.pprs.viewmodels.*;

import com.gangoffours.pprs.pprs.common.AuthControllerBase;
import com.gangoffours.pprs.pprs.repositories.IUserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

@Controller
public class DrugSearchController extends AuthControllerBase {

    private IPatientService _patientService;
    private IDrugSearchService _drugSearchService;

    public DrugSearchController (
        IPatientService patientService,
        IUserRepository userRepository,
        IDrugSearchService drugSearchService) 
    {
        super(userRepository);
        _patientService = patientService;
        _drugSearchService = drugSearchService;
    }

    @ModelAttribute("PageTitle")
    public String getPageTitle() {
        return "Drug Search";
    }

    @GetMapping("/patient/{patientid}/search{drugList}Drug")   
    public String searchDrug(Model model, @PathVariable("patientid") int patientid, @PathVariable("drugList") String drugList) {

        PatientDetailModel result = _patientService.GetPatientDetail(patientid);
        model.addAttribute("PatientName", result.FirstName + ' ' + result.LastName);
        model.addAttribute("PatientID", patientid);
        model.addAttribute("DrugList", drugList);
        model.addAttribute("Drug", "");
        model.addAttribute("DrugModel", new DrugModel ("","",""));

        return "drugSearch/index";
    }

    @PostMapping("/patient/{patientid}/search{drugList}Drug")   
    public String searchDrugWithResults(DrugModel drugmodel, Model model, @PathVariable("patientid") int patientid, @PathVariable("drugList") String drugList) {

        PatientDetailModel result = _patientService.GetPatientDetail(patientid);
        model.addAttribute("PatientName", result.FirstName + ' ' + result.LastName);
        model.addAttribute("PatientID", patientid);
        model.addAttribute("DrugList", drugList);
        model.addAttribute("Drug", "");
        model.addAttribute("DrugModel", drugmodel);
        model.addAttribute("FoundDrugs", _drugSearchService.searchResults(drugmodel));

        return "drugSearch/index";
    }

}