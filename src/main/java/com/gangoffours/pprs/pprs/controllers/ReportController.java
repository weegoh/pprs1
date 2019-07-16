package com.gangoffours.pprs.pprs.controllers;

import com.gangoffours.pprs.pprs.common.AuthControllerBase;
import com.gangoffours.pprs.pprs.repositories.IUserRepository;
import com.gangoffours.pprs.pprs.services.IPatientService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/report")
public class ReportController extends AuthControllerBase {

    private IPatientService _patientService;

    public ReportController(IPatientService patientService, IUserRepository userRepository) {
        super(userRepository);
        _patientService = patientService;
    }

    // Set the PageTitle for the controller
    @ModelAttribute("PageTitle")
    public String getPageTitle() {
        return "Report";
    }

    @GetMapping()
    public String getIndex(Model model) {
        // Override PageTitle
        model.addAttribute("PageTitle", "Report Index");
        return "report/index";
    }
    
    @GetMapping("/patient")
    public String getPatientReport(Model model, String patientName){
        // Override PageTitle
        model.addAttribute("PageTitle", "Patient Search");

        if (patientName != null && patientName.length() > 0) {
            model.addAttribute("patientName", patientName);
            model.addAttribute("patients", _patientService.FindByName(patientName));
        }

        return "report/patientreport";
    }

    @PostMapping("/patient")
    public String postPatientReport(Model model){
        return "report/patientreport";
    }
}